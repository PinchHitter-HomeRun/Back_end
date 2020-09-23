package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.model.Member;

public interface IMemberRepository {
    void save(Member member);
    Member findByLoginId(String loginId);
    Member findByLoginId(String loginId, String passWord);
    Member findById(Long id);
}
