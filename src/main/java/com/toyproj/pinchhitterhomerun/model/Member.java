package com.toyproj.pinchhitterhomerun.model;

import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    @Column(name = "password", length = 255)
    private String passWord;

    @Enumerated(EnumType.STRING)
    private SnsType sns;

    @Column(length = 10)
    private String name;

    private Integer birthDay;

    @Enumerated(EnumType.STRING)
    private SexType sex;

    @Column(length = 15)
    private String phone;

    private Integer branchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_code")
    private Role role;

    private String email;

    private String address;

    private String profileImage;

    private LocalDateTime lastLoginDate;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime deletedDate;

    public Member() {
    }

    public Member(String loginId, String passWord, SnsType sns, String name, int birthDay, SexType sex, String phone, int branchId, Role role, String email, String address, String profileImage) {
        this.loginId = loginId;
        this.passWord = passWord;
        this.sns = sns;
        this.name = name;
        this.birthDay = birthDay;
        this.sex = sex;
        this.phone = phone;
        this.branchId = branchId;
        this.role = role;
        this.email = email;
        this.address = address;
        this.profileImage = profileImage;
        this.lastLoginDate = LocalDateTime.now();
        this.createdDate = LocalDateTime.now();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateLastLoginDate() {
        this.lastLoginDate = LocalDateTime.now();
    }

    public void updateUpdatedDate() {
        this.updatedDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", passWord='" + passWord + '\'' +
                ", sns='" + sns + '\'' +
                ", name='" + name + '\'' +
                ", birthDay=" + birthDay +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", branchId=" + branchId +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", lastLoginDate=" + lastLoginDate +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", deletedDate=" + deletedDate +
                '}';
    }
}
