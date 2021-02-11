package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.BranchRequest;
import com.toyproj.pinchhitterhomerun.type.AcceptType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface IBranchRequestRepository {
    boolean save(BranchRequest branchRequest);
    BranchRequest findById(Long id);
    Collection<BranchRequest> findByBranchId(Long branchId);
    BranchRequest findByMemberId(Long memberId);
    int updateDeleteTime(Long requestId, LocalDateTime localDateTime);
    int updateAccept(Long requestId, AcceptType acceptType, LocalDateTime localDateTime);
}
