package com.toyproj.pinchhitterhomerun.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeManager {
    public static LocalDateTime now() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
