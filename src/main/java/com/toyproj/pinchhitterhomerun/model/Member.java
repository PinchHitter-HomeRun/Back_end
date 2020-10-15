package com.toyproj.pinchhitterhomerun.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    @Column(name = "password")
    private String passWord;

    @Enumerated(EnumType.STRING)
    private SnsType sns;

    @Column(length = 10)
    private String name;

    private String birthDay;

    @Enumerated(EnumType.STRING)
    private SexType sex;

    @Column(length = 15)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_code")
    private Role role;

    private String profileImage;

    private LocalDateTime lastLoginDate;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime deletedDate;

    public Member() {
    }

    public Member(String loginId, String passWord, SnsType sns, String name, String birthDay, SexType sex, String phone, Branch branch, Role role, String profileImage) {
        this.loginId = loginId;
        this.passWord = passWord;
        this.sns = sns;
        this.name = name;
        this.birthDay = birthDay;
        this.sex = sex;
        this.phone = phone;
        this.branch = branch;
        this.role = role;
        this.profileImage = profileImage;
        this.createdDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateLastLoginDate() {
        this.lastLoginDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void updateUpdatedDate() {
        this.updatedDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void updateDeletedDate() {
        this.deletedDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", passWord='" + passWord + '\'' +
                ", sns=" + sns +
                ", name='" + name + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", branch=" + branch +
                ", role=" + role +
                ", profileImage='" + profileImage + '\'' +
                ", lastLoginDate=" + lastLoginDate +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", deletedDate=" + deletedDate +
                '}';
    }
}
