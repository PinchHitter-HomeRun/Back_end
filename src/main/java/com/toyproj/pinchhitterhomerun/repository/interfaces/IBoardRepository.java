package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.model.Board;
import com.toyproj.pinchhitterhomerun.model.Branch;
import com.toyproj.pinchhitterhomerun.model.Brand;
import com.toyproj.pinchhitterhomerun.model.Member;

import java.util.List;

public interface IBoardRepository {
    void save(Board board);
    List<Board> findById(long id);
    List<Board> findByMemberId(long id);
    List<Board> findByMemberLoginId(long id);
    List<Board> findByMemberBranchName(String branchName);
    List<Board> findByBrandName(String brandName);
}
