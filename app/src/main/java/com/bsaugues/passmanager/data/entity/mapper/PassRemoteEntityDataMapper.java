package com.bsaugues.passmanager.data.entity.mapper;

import com.bsaugues.passmanager.data.entity.model.PassEntity;
import com.bsaugues.passmanager.data.entity.remote.PassRemoteEntity;
import com.bsaugues.passmanager.data.exception.MapperException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PassRemoteEntityDataMapper {

    @Inject
    public PassRemoteEntityDataMapper() {
    }

    public List<PassEntity> toEntities(List<PassRemoteEntity> remoteEntities) {
        List<PassEntity> entities = new ArrayList<>();
        if (remoteEntities != null) {
            for (PassRemoteEntity remoteEntity : remoteEntities) {
                entities.add(toEntity(remoteEntity));
            }
        }
        return entities;
    }

    public PassEntity toEntity(PassRemoteEntity remoteEntity) {
        if (remoteEntity != null) {
            PassEntity entity = new PassEntity();
            entity.setType(remoteEntity.getType());
            entity.setAmount(remoteEntity.getAmount());
            return entity;
        }
        throw new MapperException();
    }
}
