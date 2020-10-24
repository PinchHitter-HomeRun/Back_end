package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.model.Branch;

import java.util.List;

public interface IBranchRepository {

    Branch findById(Long id);
    Branch findByBrandAndName(Long brandId, String name);
    Branch findByAddress(String address);
    // 시, 구, 검색어
    List<Branch> searchByKeywordWithBrandId(Long brandId, String city, String sub, String text);
    List<Branch> findByBrandId(Long brandId);
    Branch findByMemberId(Long memberId);

}
