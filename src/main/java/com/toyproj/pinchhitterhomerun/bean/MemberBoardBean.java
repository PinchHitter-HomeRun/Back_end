package com.toyproj.pinchhitterhomerun.bean;

import com.toyproj.pinchhitterhomerun.entity.Member;
import lombok.Getter;

import java.io.Serializable;

public class MemberBoardBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    private Long id;

    @Getter
    private String name;

    public void setMemberProperty(Member member) {
        this.id = member.getId();
        this.name = member.getName();
    }
}
