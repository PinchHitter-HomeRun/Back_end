package com.toyproj.pinchhitterhomerun.response;

import com.toyproj.pinchhitterhomerun.bean.MemberBoardBean;
import com.toyproj.pinchhitterhomerun.type.MatchType;
import com.toyproj.pinchhitterhomerun.type.PayType;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class BoardContentRes {
    private Long id;
    private MemberBoardBean author;
    private String title;
    private String content;
    private LocalDateTime writeData;
    private LocalDateTime starDate;
    private LocalDateTime endDate;
    private int payType;
    private int pay;
    private int matchType;
}
