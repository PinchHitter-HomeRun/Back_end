package com.toyproj.pinchhitterhomerun.model;

import lombok.Getter;

@Getter
public class MemberJoin {
    private Member member;
    private String answer;

    public MemberJoin(Member member, String answer) {
        this.member = member;
        this.answer = answer;
    }
}
