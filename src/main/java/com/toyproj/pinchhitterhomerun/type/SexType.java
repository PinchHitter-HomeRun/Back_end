package com.toyproj.pinchhitterhomerun.type;

import java.util.Arrays;

public enum SexType {
    Male(1),
    Female(2);

    SexType(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }

    public static SexType fromInt(int value) {
        return Arrays.stream(SexType.values()).filter(x -> x.getValue() == value).findFirst().orElse(null);
    }
}
