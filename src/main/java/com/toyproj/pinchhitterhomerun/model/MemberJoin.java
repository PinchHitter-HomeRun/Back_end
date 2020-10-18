package com.toyproj.pinchhitterhomerun.model;

import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberJoin {
//    private final Member member;
//    private final Long hintId;
//    private final String answer;
    private String loginId;

    private String passWord;

    private SnsType sns;

    private String name;

    private String birthDay;

    private SexType sex;

    private String phone;

    private Long branchId;

    private String roleName;

    private Long hintId;

    private String answer;
}
