package com.bsaugues.passmanager.data.manager;

import com.bsaugues.passmanager.data.entity.model.ReservationEntity;
import com.bsaugues.passmanager.data.entity.remote.ReservationRemoteEntity;

import io.reactivex.Observable;

public interface CacheManager {
    Observable<ReservationEntity> getLastScannedReservation();

    void saveLastScannedReservation(ReservationEntity reservationEntity);

    void resetLastScannedReservation();
}
