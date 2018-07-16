package com.bsaugues.passmanager.data.entity;

import java.util.ArrayList;
import java.util.List;

public class ReservationEntity {

    private String id;
    private String ownerFirstName;
    private String ownerLastName;
    private Long lastScanDateTimestamp;
    private List<PassEntity> passList;

    public ReservationEntity() {
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

    public List<PassEntity> getPassList() {
        return passList;
    }

    public void setPassList(List<PassEntity> passList) {
        this.passList = passList;
    }
}
