package com.toyproj.pinchhitterhomerun.entity;

import com.toyproj.pinchhitterhomerun.type.MatchType;
import com.toyproj.pinchhitterhomerun.type.PayType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Board extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Long brandId;

    private Long branchId;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    private int pay;

    @Enumerated(EnumType.STRING)
    private MatchType matchType = MatchType.Waiting;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime matchedDate;

    public void updateDeleteTime() {
        this.setDeletedDate();
    }

    public void writeBoard(Member member, String title,
                           String content, PayType payType, int pay,
                           LocalDateTime startDate, LocalDateTime endDate) {
        this.member = member;
        this.brandId = member.getBranch().getBrand().getId();
        this.branchId = member.getBranch().getId();
        this.title = title;
        this.content = content;
        this.payType = payType;
        this.pay = pay;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateBoard(String title, String content, PayType payType,
                            int pay, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.content = content;
        this.payType = payType;
        this.pay = pay;
        this.startDate = startDate;
        this.endDate = endDate;

        this.setUpdatedDate();
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", branchId=" + branchId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", payType=" + payType +
                ", pay=" + pay +
                ", matchType=" + matchType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", matchedDate=" + matchedDate +
                ", deletedDate=" + getDeletedDate() +
                '}';
    }
}
