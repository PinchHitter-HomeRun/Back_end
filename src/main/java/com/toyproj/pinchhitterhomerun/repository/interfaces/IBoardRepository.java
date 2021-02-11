package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.Board;
import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Brand;
import com.toyproj.pinchhitterhomerun.entity.Member;

import java.util.Collection;
import java.util.List;

public interface IBoardRepository {
    boolean save(Board board);
    Collection<Board> findAll();
    Board findById(long id);
    Collection<Board> findByMember(Member member);
    Collection<Board> findByBranchId(Long branchId);
    Collection<Board> findByBrandId(Long brandId);
}
