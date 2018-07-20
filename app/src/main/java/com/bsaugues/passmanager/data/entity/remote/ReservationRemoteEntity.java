package com.bsaugues.passmanager.data.entity.remote;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReservationRemoteEntity {

    @SerializedName("id")
    private String id;

    @SerializedName("first_name")
    private String ownerFirstName;

    @SerializedName("last_name")
    private String ownerLastName;

    @SerializedName("last_scan")
    private Long lastScanDateTimestamp;

    @SerializedName("pass_list")
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
