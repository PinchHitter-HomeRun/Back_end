package com.toyproj.pinchhitterhomerun.request;

import lombok.Getter;

@Getter
public class MemberJoinReq {
    private String loginId;

    private String passWord;

    private int snsType;

    private String name;

    private String birthDay;

    private int sexType;

    private String phone;

    private Long branchId;

    private String roleName;

    private Long hintId;

    private String answer;
}
