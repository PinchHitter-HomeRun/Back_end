package com.toyproj.pinchhitterhomerun.entity;

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

    @Override
    public String toString() {
        return "MemberJoin{" +
                "loginId='" + loginId + '\'' +
                ", passWord='" + passWord + '\'' +
                ", sns=" + sns +
                ", name='" + name + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", branchId=" + branchId +
                ", roleName='" + roleName + '\'' +
                ", hintId=" + hintId +
                ", answer='" + answer + '\'' +
                '}';
    }
}
