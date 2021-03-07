package com.toyproj.pinchhitterhomerun.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public class NotificationTitleResultBean implements Serializable  {

    private static final long serialVersionUID = 1L;

    private Long id;

    private MemberBoardBean author;

    private String title;

    private LocalDateTime writeDate;

    private boolean isMain;
}
