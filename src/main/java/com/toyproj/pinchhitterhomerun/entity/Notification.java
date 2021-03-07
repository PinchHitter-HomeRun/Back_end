package com.toyproj.pinchhitterhomerun.entity;

import com.toyproj.pinchhitterhomerun.util.TimeManager;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Notification extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    private String title;

    private String content;

    private boolean isMain;

    public void writeNotification(Member adminMember, String title, String content, boolean isMain) {
        this.member = adminMember;
        this.title = title;
        this.content = content;
        this.isMain = isMain;
    }

    public void updateNotification(String title, String content, boolean isMain) {
        this.title = title;
        this.content = content;
        this.isMain = isMain;
    }

    public void deleteNotification() {
        setDeletedDate();
    }
}
