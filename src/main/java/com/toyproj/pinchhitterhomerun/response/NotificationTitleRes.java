package com.toyproj.pinchhitterhomerun.response;

import com.toyproj.pinchhitterhomerun.bean.MemberBoardBean;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class NotificationTitleRes {
    private Long id;

    private MemberBoardBean author;

    private String title;

    private LocalDateTime writeDate;

    private boolean isMain;
}
