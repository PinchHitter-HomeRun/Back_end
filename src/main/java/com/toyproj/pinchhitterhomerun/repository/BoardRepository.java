package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.bean.BoardTitleResultBean;
import com.toyproj.pinchhitterhomerun.entity.Board;
import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Brand;
import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IBoardRepository;
import com.toyproj.pinchhitterhomerun.type.PayType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public class BoardRepository implements IBoardRepository {

    private final EntityManager em;

    public BoardRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean save(Board board) {
        try {
            em.persist(board);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Collection<Board> findAll() {
        return em.createQuery("select bbs from Board bbs where bbs.deletedDate is null order by bbs.createdDate", Board.class)
                .getResultList();
    }

    @Override
    public Board findById(Long id) {
        try {
            return em.createQuery("select bbs from Board bbs where bbs.id = :id and bbs.deletedDate is null", Board.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Collection<Board> findByMember(Member member) {
        return em.createQuery("select bbs from Board bbs where bbs.member = :member and bbs.deletedDate is null order by bbs.createdDate", Board.class)
                .setParameter("member", member)
                .getResultList();
    }

    @Override
    public Collection<Board> findByMembers(Collection<Member> members) {
        return em.createQuery("select bbs from Board bbs where bbs.member in :members and bbs.deletedDate is null order by bbs.createdDate", Board.class)
                .setParameter("members", members)
                .getResultList();
    }

    @Override
    public Collection<Board> findByBranchId(Long branchId) {
        return em.createQuery("select bbs from Board bbs where bbs.branchId = :branchId and bbs.deletedDate is null order by bbs.createdDate", Board.class)
                .setParameter("branchId", branchId)
                .getResultList();
    }

    @Override
    public Collection<Board> findByBrandId(Long brandId) {
        return em.createQuery("select bbs from Board bbs where bbs.brandId = :brandId and bbs.deletedDate is null order by bbs.createdDate", Board.class)
                .setParameter("brandId", brandId)
                .getResultList();
    }

    @Override
    public int deleteAll(Member member, LocalDateTime deleteTime) {
        final var updatedRow = em.createQuery("update Board bbs set bbs.deletedDate = :deleteTime where bbs.member = :member")
                .setParameter("deleteTime", deleteTime)
                .setParameter("member", member)
                .executeUpdate();

        em.flush();
        em.clear();

        return updatedRow;
    }
}
