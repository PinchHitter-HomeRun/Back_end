package com.toyproj.pinchhitterhomerun.type;

import java.util.Arrays;

public enum AcceptType {
    Accept(1),
    Deny(2);

    AcceptType(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }

    public static AcceptType fromInt(int value) {
        return Arrays.stream(AcceptType.values()).filter(x -> x.getValue() == value).findFirst().orElse(null);
    }
}
