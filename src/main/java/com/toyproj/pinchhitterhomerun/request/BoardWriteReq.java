package com.toyproj.pinchhitterhomerun.request;

import com.toyproj.pinchhitterhomerun.type.PayType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardWriteReq {
    private Long memberId;
    private String title;
    private String content;
    private int payType;
    private int pay;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
