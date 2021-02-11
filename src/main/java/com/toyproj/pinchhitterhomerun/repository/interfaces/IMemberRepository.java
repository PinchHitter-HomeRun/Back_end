package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface IMemberRepository {
    boolean save(Member member);
    Member findByLoginId(String loginId);
    Member findByLoginId(String loginId, String passWord);
    Member findById(Long id);
    Member findLoginIdByInfo(String name, String birth);
    Member findByLoginIdAndBirthDay(String loginId, String birthDay);
    List<Member> findByBranchId(Long branchId);
    int updateBranch(Long memberId, Branch branch);
    int updateLastLoginDate(Long memberId, LocalDateTime dateTime);
    int updateDeleteTime(Long memberId, LocalDateTime dateTime);
}
