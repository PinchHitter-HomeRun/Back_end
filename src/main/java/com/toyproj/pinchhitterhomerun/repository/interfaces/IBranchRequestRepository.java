package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.BranchRequest;

import java.util.List;

public interface IBranchRequestRepository {

    boolean save(BranchRequest branchRequest);
    BranchRequest findById(Long id);
    List<BranchRequest> findByBranchId(Long branchId);
    BranchRequest findByMemberId(Long memberId);
}
