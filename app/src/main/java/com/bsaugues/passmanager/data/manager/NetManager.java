package com.bsaugues.passmanager.data.manager;

import com.bsaugues.passmanager.data.entity.remote.ReservationRemoteEntity;

import io.reactivex.Observable;

public interface NetManager {
    Observable<ReservationRemoteEntity> getReservationFromId(String id);
}
