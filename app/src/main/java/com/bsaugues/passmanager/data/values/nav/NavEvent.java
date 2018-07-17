package com.bsaugues.passmanager.data.values.nav;

public class NavEvent {

    private NavEventTypeValues type;

    public NavEvent(NavEventTypeValues type) {
        this.type = type;
    }

    public NavEventTypeValues getType() {
        return type;
    }

}
