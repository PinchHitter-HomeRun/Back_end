package com.toyproj.pinchhitterhomerun.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationWriteReq {
    private Long adminId;
    private String title;
    private String content;
    private Boolean isMain;
}
