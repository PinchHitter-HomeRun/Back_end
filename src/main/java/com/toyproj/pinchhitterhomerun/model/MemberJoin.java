package com.toyproj.pinchhitterhomerun.model;

import lombok.Getter;

@Getter
public class MemberJoin {
    private final Member member;
    private final Long hintId;
    private final String answer;

    public MemberJoin(Member member, Long hintId, String answer) {
        this.member = member;
        this.hintId = hintId;
        this.answer = answer;
    }
}
