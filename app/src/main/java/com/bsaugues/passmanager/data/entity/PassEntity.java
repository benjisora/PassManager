package com.bsaugues.passmanager.data.entity;

import com.bsaugues.passmanager.data.values.PassTypeValues;

public class PassEntity {

    private PassTypeValues type;
    private Long amount;

    public PassEntity() {
        this.type = PassTypeValues.UNKNOWN;
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
