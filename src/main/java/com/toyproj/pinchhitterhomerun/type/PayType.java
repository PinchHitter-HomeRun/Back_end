package com.toyproj.pinchhitterhomerun.type;

import java.util.Arrays;

public enum PayType {
    Hour(1),
    Day(2);

    PayType(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }

    public static PayType fromInt(int value) {
        return Arrays.stream(PayType.values()).filter(x -> x.getValue() == value).findFirst().orElse(null);
    }
}
