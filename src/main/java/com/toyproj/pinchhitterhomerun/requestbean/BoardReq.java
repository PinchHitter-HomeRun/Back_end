package com.toyproj.pinchhitterhomerun.requestbean;

import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.type.MatchType;
import com.toyproj.pinchhitterhomerun.type.PayType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardReq {
    private String title;
    private String content;
    private PayType payType;
    private int pay;
    private MatchType matchType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime matchedDate;
}
