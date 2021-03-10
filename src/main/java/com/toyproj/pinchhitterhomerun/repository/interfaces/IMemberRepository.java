package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.entity.Role;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface IMemberRepository {
    boolean save(Member member);
    Member findByLoginId(String loginId);
    Member findByLoginId(String loginId, String passWord);
    Member findById(Long id);
    Member findLoginIdByInfo(String name, String birth);
    Member findByLoginIdAndBirthDay(String loginId, String birthDay);
    Collection<Member> findByBranchId(Long branchId);
    Collection<Member> findByContainsName(String name);

    int updateBranch(Long memberId, Branch branch);
    int updateRole(Long memberId, Role role);
}
