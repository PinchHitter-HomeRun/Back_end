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
    @JoinColumn(name = "member_id")
    private Member memberId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hint_id")
    private PasswordHint hintId;

    private String answer;

    public MemberPasswordHint(Member memberId, PasswordHint hintId, String answer) {
        this.memberId = memberId;
        this.hintId = hintId;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "MemberPasswordHint{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", hintId=" + hintId +
                ", answer='" + answer + '\'' +
                '}';
    }
}
