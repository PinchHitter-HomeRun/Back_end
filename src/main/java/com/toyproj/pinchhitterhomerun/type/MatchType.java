package com.toyproj.pinchhitterhomerun.type;

import java.util.Arrays;

public enum  MatchType {
    Waiting(1),
    Matching(2),
    Matched(3);

    MatchType(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }

    public static MatchType fromInt(int value) {
        return Arrays.stream(MatchType.values()).filter(x -> x.getValue() == value).findFirst().orElse(null);
    }
}
