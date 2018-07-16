package com.bsaugues.passmanager.data.entity.mapper;

import com.bsaugues.passmanager.data.entity.ReservationEntity;
import com.bsaugues.passmanager.data.entity.remote.ReservationRemoteEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ReservationRemoteEntityDataMapper {

    private PassRemoteEntityDataMapper passRemoteEntityDataMapper;

    @Inject
    public ReservationRemoteEntityDataMapper(PassRemoteEntityDataMapper passRemoteEntityDataMapper) {
        this.passRemoteEntityDataMapper = passRemoteEntityDataMapper;
    }

    public ReservationEntity toEntity(ReservationRemoteEntity remoteEntity) {
        if (remoteEntity != null) {
            ReservationEntity entity = new ReservationEntity();
            entity.setId(remoteEntity.getId());
            entity.setOwnerFirstName(remoteEntity.getOwnerFirstName());
            entity.setOwnerLastName(remoteEntity.getOwnerLastName());
            entity.setLastScanDateTimestamp(remoteEntity.getLastScanDateTimestamp());
            entity.setPassList(passRemoteEntityDataMapper.toEntities(remoteEntity.getPassList()));
            return entity;
        }
        return null;
    }
}
