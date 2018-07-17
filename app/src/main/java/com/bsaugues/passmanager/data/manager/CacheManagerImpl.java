package com.bsaugues.passmanager.data.manager;

import com.bsaugues.passmanager.data.entity.model.ReservationEntity;
import com.bsaugues.passmanager.data.exception.EmptyReservationEntityException;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CacheManagerImpl implements CacheManager {

    private ReservationEntity reservationEntity;

    @Inject
    public CacheManagerImpl() {

    }

    @Override
    public Observable<ReservationEntity> getLastScannedReservation() {
        if (reservationEntity != null) {
            return Observable.just(reservationEntity);
        }

        throw new EmptyReservationEntityException();
    }

    @Override
    public void saveLastScannedReservation(ReservationEntity reservationEntity) {
        this.reservationEntity = reservationEntity;
    }

    @Override
    public void resetLastScannedReservation() {
        this.reservationEntity = null;
    }
}
