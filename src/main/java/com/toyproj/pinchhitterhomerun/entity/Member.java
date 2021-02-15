package com.toyproj.pinchhitterhomerun.entity;

import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import com.toyproj.pinchhitterhomerun.util.TimeManager;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

//    private LocalDateTime createdDate;
//
//    private LocalDateTime updatedDate;
//
//    private LocalDateTime deletedDate;

    public Member(String loginId, String passWord, SnsType sns, String name, String birthDay, SexType sex, String phone, Branch branch, Role role) {
        this.loginId = loginId;
        this.passWord = passWord;
        this.sns = sns;
        this.name = name;
        this.birthDay = birthDay;
        this.sex = sex;
        this.phone = phone;
        this.branch = branch;
        this.role = role;
    }

    public Member(Long id) {
        this.id = id;
    }

    public void updateLastLoginDate() {
        this.lastLoginDate = TimeManager.now();
    }

    public void updateDeletedDate() {
        this.setDeletedDate();
    }

    public void updatePassWord(String passWord) {
        this.passWord = passWord;
    }

    public void updateBranch(Branch branch) {
        this.branch = branch;
    }
}