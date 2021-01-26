package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.Board;

import java.util.List;

public interface IBoardRepository {
    boolean save(Board board);
    Board findById(long id);
    List<Board> findByMemberId(long id);
    List<Board> findByMemberLoginId(String loginId);
    List<Board> findByBranchName(String branchName);
    List<Board> findByBrandName(String brandName);
}
