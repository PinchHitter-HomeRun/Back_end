package com.toyproj.pinchhitterhomerun.model;

import com.toyproj.pinchhitterhomerun.type.MatchType;
import com.toyproj.pinchhitterhomerun.type.PayType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class board extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    private int pay;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime matchedDate;
}
