package com.bsaugues.passmanager.data.repository;

import com.bsaugues.passmanager.data.entity.ReservationEntity;
import com.bsaugues.passmanager.data.entity.mapper.ReservationRemoteEntityDataMapper;
import com.bsaugues.passmanager.data.entity.remote.ReservationRemoteEntity;
import com.bsaugues.passmanager.data.manager.NetManager;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@Singleton
public class ContentRepository {

    private NetManager netManager;
    private ReservationRemoteEntityDataMapper reservationRemoteEntityDataMapper;

    @Inject
    public ContentRepository(NetManager netManager, ReservationRemoteEntityDataMapper reservationRemoteEntityDataMapper) {
        this.netManager = netManager;
        this.reservationRemoteEntityDataMapper = reservationRemoteEntityDataMapper;
    }

    public Observable<ReservationEntity> getReservationDetails(final String id) {
        return Observable.defer(new Callable<ObservableSource<ReservationEntity>>() {
            @Override
            public ObservableSource<ReservationEntity> call() {
                return netManager.getReservationFromId(id).map(new Function<ReservationRemoteEntity, ReservationEntity>() {
                    @Override
                    public ReservationEntity apply(ReservationRemoteEntity reservationRemoteEntity) {
                        return reservationRemoteEntityDataMapper.toEntity(reservationRemoteEntity);
                    }
                });
            }
        });
    }
}
