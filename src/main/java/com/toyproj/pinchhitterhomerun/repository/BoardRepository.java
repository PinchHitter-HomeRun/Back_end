package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.entity.Board;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IBoardRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
    public Board findById(long id) {
        return em.find(Board.class, id);
    }

    @Override
    public List<Board> findByMemberId(long id) {
        return em.createQuery("select bbs from Board bbs where bbs.id = :id", Board.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Board> findByMemberLoginId(String loginId) {
        return em.createQuery("select bbs from Board bbs where bbs.member.loginId = :loginId", Board.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }

    @Override
    public List<Board> findByBranchName(String branchName) {
        return em.createQuery("select bbs from Board bbs " +
                "where bbs.brandId = (select b.id from Branch b where b.name = :branchName)", Board.class)
                .setParameter("branchName", branchName)
                .getResultList();
    }

    @Override
    public List<Board> findByBrandName(String brandName) {
        return em.createQuery("select bbs from Board bbs " +
                "where bbs.brandId = (select b.id from Brand b where b.name = :brandName)", Board.class)
                .setParameter("brandName", brandName)
                .getResultList();
    }
}
