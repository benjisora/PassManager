package com.bsaugues.passmanager.data.entity.remote;

import com.bsaugues.passmanager.data.values.PassTypeValues;

public class PassRemoteEntity {

    private PassTypeValues type;
    private Long amount;

    public PassRemoteEntity() {
        this.type = PassTypeValues.UNKNOWN;
    }

    public PassRemoteEntity(PassTypeValues type, Long amount) {
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
