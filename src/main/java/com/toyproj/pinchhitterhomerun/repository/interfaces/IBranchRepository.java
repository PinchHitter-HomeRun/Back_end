package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.Branch;

import java.util.Collection;
import java.util.List;

public interface IBranchRepository {

    Collection<Branch> findAll();
    Branch findById(Long id);
    Branch findByBrandAndName(Long brandId, String name);
    // 시, 구, 검색어
    Collection<Branch> searchByKeywordWithBrandId(Long brandId, String city, String sub, String text);
    Collection<Branch> findByBrandId(Long brandId);
    Branch findByMemberId(Long memberId);
    Branch findByName(String name);
    Branch findByAddress(String address);
    int updateMemberBranch(Long memberId, Branch branch);
}
