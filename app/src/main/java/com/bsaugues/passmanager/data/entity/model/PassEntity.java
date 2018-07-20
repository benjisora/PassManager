package com.bsaugues.passmanager.data.entity.model;

import com.bsaugues.passmanager.data.values.PassTypeValues;

public class PassEntity {

    private PassTypeValues type;
    private Integer amount;

    public PassEntity() {
        this.type = PassTypeValues.UNKNOWN;
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
