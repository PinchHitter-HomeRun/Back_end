package com.toyproj.pinchhitterhomerun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member_hint")
@Getter
@NoArgsConstructor
public class MemberPasswordHint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    private PasswordHint hint;

    private String answer;

    public MemberPasswordHint(Member memberId, PasswordHint hintId, String answer) {
        this.member = memberId;
        this.hint = hintId;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "MemberPasswordHint{" +
                "id=" + id +
                ", memberId=" + member +
                ", hintId=" + hint +
                ", answer='" + answer + '\'' +
                '}';
    }
}
