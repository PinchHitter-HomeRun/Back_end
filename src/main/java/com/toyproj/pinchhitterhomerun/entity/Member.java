package com.toyproj.pinchhitterhomerun.entity;

import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Entity
@Getter
@Setter
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
        this.lastLoginDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void updateUpdatedDate() {
        this.setUpdatedDate();
    }

    public void updateDeletedDate() {
        this.setDeletedDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(loginId, member.loginId) &&
                Objects.equals(passWord, member.passWord) &&
                sns == member.sns &&
                Objects.equals(name, member.name) &&
                Objects.equals(birthDay, member.birthDay) &&
                sex == member.sex &&
                Objects.equals(phone, member.phone) &&
                Objects.equals(branch, member.branch) &&
                Objects.equals(role, member.role) &&
                Objects.equals(profileImage, member.profileImage) &&
                Objects.equals(lastLoginDate, member.lastLoginDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passWord, sns, name, birthDay, sex, phone, branch, role, profileImage, lastLoginDate);
    }
}