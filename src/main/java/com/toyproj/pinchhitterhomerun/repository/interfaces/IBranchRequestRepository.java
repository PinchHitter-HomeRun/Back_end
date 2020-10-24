package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.model.BranchRequest;

import java.util.List;

public interface IBranchRequestRepository {

    void save(BranchRequest branchRequest);
    BranchRequest findById(Long id);
    List<BranchRequest> findByBranchId(Long branchId);
    BranchRequest findByMemberId(Long memberId);
}
