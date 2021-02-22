package com.toyproj.pinchhitterhomerun.type;

import java.util.Arrays;

public enum SnsType {
    None(1),
    Kakao(2),
    Google(3),
    Facebook(4);

    SnsType(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }

    public static SnsType fromInt(int value) {
        return Arrays.stream(SnsType.values()).filter(x -> x.getValue() == value).findFirst().orElse(null);
    }
}
