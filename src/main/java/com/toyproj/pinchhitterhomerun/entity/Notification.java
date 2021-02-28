package com.toyproj.pinchhitterhomerun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Notification extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private boolean isMain;

    public void writeNotification(String title, String content, boolean isMain) {
        this.title = title;
        this.content = content;
        this.isMain = isMain;
    }
}
