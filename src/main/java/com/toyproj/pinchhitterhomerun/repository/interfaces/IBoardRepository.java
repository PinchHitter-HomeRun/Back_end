package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.Board;
import com.toyproj.pinchhitterhomerun.entity.Member;

import java.time.LocalDateTime;
import java.util.Collection;

public interface IBoardRepository {
    boolean save(Board board);
    Collection<Board> findAll();
    Board findById(Long id);
    Collection<Board> findByMember(Member member);
    Collection<Board> findByMembers(Collection<Member> members);
    Collection<Board> findByBranchId(Long branchId);
    Collection<Board> findByBrandId(Long brandId);

    int deleteAll(Member member, LocalDateTime deleteTime);
}
