package com.toyproj.pinchhitterhomerun.bean;

import com.toyproj.pinchhitterhomerun.type.MatchType;
import com.toyproj.pinchhitterhomerun.type.PayType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BoardTitleResultBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private MemberBoardBean author;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private LocalDateTime writeDate;

    @Getter
    @Setter
    private LocalDateTime starDate;

    @Getter
    @Setter
    private LocalDateTime endDate;

    @Getter
    @Setter
    private PayType payType;

    @Getter
    @Setter
    private int pay;

    @Getter
    @Setter
    private MatchType matchType;
}
