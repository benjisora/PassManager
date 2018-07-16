package com.bsaugues.passmanager.data.entity.remote;

import com.bsaugues.passmanager.data.entity.PassEntity;

import java.util.ArrayList;
import java.util.List;

public class ReservationRemoteEntity {

    private String id;
    private String ownerFirstName;
    private String ownerLastName;
    private Long lastScanDateTimestamp;
    private List<PassRemoteEntity> passList;

    public ReservationRemoteEntity() {
        this.passList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public Long getLastScanDateTimestamp() {
        return lastScanDateTimestamp;
    }

    public void setLastScanDateTimestamp(Long lastScanDateTimestamp) {
        this.lastScanDateTimestamp = lastScanDateTimestamp;
    }

    public List<PassRemoteEntity> getPassList() {
        return passList;
    }

    public void setPassList(List<PassRemoteEntity> passList) {
        this.passList = passList;
    }
}
