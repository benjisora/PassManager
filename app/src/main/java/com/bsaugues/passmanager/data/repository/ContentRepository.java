package com.bsaugues.passmanager.data.repository;

import com.bsaugues.passmanager.data.entity.model.ReservationEntity;
import com.bsaugues.passmanager.data.entity.mapper.ReservationRemoteEntityDataMapper;
import com.bsaugues.passmanager.data.entity.remote.ReservationRemoteEntity;
import com.bsaugues.passmanager.data.manager.CacheManager;
import com.bsaugues.passmanager.data.manager.NetManager;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

@Singleton
public class ContentRepository {

    private CacheManager cacheManager;
    private NetManager netManager;
    private ReservationRemoteEntityDataMapper reservationRemoteEntityDataMapper;

    @Inject
    public ContentRepository(CacheManager cacheManager, NetManager netManager, ReservationRemoteEntityDataMapper reservationRemoteEntityDataMapper) {
        this.cacheManager = cacheManager;
        this.netManager = netManager;
        this.reservationRemoteEntityDataMapper = reservationRemoteEntityDataMapper;
    }

    public Observable<Boolean> receiveReservationDetailsFromServer(final String id) {
        return Observable.defer(new Callable<ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> call() {
                return netManager.getReservationFromId(id)
                        .map(new Function<ReservationRemoteEntity, ReservationEntity>() {
                            @Override
                            public ReservationEntity apply(ReservationRemoteEntity reservationRemoteEntity) {
                                return reservationRemoteEntityDataMapper.toEntity(reservationRemoteEntity);
                            }
                        })
                        .doOnNext(new Consumer<ReservationEntity>() {
                            @Override
                            public void accept(ReservationEntity reservationEntity) {
                                cacheManager.saveLastScannedReservation(reservationEntity);
                            }
                        })
                        .doOnError(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                cacheManager.resetLastScannedReservation();
                            }
                        })
                        .map(new Function<ReservationEntity, Boolean>() {
                            @Override
                            public Boolean apply(ReservationEntity reservationEntity) {
                                return reservationEntity != null;
                            }
                        });
            }
        });
    }

    public Observable<ReservationEntity> getLastScannedReservationDetails() {
        return Observable.defer(new Callable<ObservableSource<ReservationEntity>>() {
            @Override
            public ObservableSource<ReservationEntity> call() {
                return cacheManager.getLastScannedReservation();
            }
        });
    }
}
