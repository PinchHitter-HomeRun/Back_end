package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.bean.BoardTitleResultBean;
import com.toyproj.pinchhitterhomerun.entity.Board;
import com.toyproj.pinchhitterhomerun.entity.Member;

import java.time.LocalDateTime;
import java.util.Collection;

public interface IBoardRepository {
    boolean save(Board board);
    Collection<Board> findAll(int page, int count);
    Board findById(Long id);
    Collection<Board> findByMember(Member member, int page, int count);
    Collection<Board> findByMembers(Collection<Member> members, int page, int count);
    Collection<Board> findByBranchId(Long branchId, int page, int count);
    Collection<Board> findByBrandId(Long brandId, int page, int count);
    Collection<Board> findAllBoardByMember(Member member);

    int deleteAll(Member member, LocalDateTime deleteTime);
}
