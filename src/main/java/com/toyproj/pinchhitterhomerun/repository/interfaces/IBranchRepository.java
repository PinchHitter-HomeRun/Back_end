package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.model.Branch;

import java.util.List;

public interface IBranchRepository {

    // 시, 구, 검색어
    List<Branch> searchByName(String city, String sub, String text);
    List<Branch> findByBrandId(Long brandId);

}
