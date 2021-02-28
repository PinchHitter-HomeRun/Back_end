package com.toyproj.pinchhitterhomerun.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NotificationTitleResultBean implements Serializable  {

    private static final long serialVersionUID = 1L;

    private Long id;

    private MemberBoardBean author;

    private String title;

    private LocalDateTime writeDate;

    private boolean isMain;
}
