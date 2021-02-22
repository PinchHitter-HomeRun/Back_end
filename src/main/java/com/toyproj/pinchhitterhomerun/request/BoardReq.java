package com.toyproj.pinchhitterhomerun.request;

import com.toyproj.pinchhitterhomerun.type.PayType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardReq {
    private String title;
    private String content;
    private PayType payType;
    private int pay;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
