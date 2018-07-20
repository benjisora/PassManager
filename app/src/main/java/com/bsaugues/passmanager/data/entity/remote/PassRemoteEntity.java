package com.bsaugues.passmanager.data.entity.remote;

import com.bsaugues.passmanager.data.values.PassTypeValues;
import com.google.gson.annotations.SerializedName;

public class PassRemoteEntity {

    @SerializedName("type")
    private PassTypeValues type;

    @SerializedName("amount")
    private Integer amount;

    public PassRemoteEntity() {
        this.type = PassTypeValues.UNKNOWN;
    }

    public PassRemoteEntity(PassTypeValues type, Integer amount) {
        this();
        this.type = type;
        this.amount = amount;
    }

    public PassTypeValues getType() {
        return type;
    }

    public void setType(PassTypeValues type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
