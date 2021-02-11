package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.entity.BranchRequest;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IBranchRequestRepository;
import com.toyproj.pinchhitterhomerun.type.AcceptType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public class BranchRequestRepository implements IBranchRequestRepository {

    private final EntityManager em;

    public BranchRequestRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean save(BranchRequest branchRequest) {
        try {
            em.persist(branchRequest);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public BranchRequest findById(Long id) {
        try {
            return em.createQuery("select br from BranchRequest br where br.id = :id and br.updatedDate is null and br.deletedDate is null", BranchRequest.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Collection<BranchRequest> findByBranchId(Long branchId) {
        return em.createQuery("select br from BranchRequest br where br.branchId = :branchId and br.updatedDate is null and br.deletedDate is null", BranchRequest.class)
                .setParameter("branchId", branchId)
                .getResultList();
    }

    @Override
    public BranchRequest findByMemberId(Long memberId) {
        try {
            return em.createQuery("select br from BranchRequest br where br.memberId = :memberId and br.updatedDate is null and br.deletedDate is null", BranchRequest.class)
                    .setParameter("memberId", memberId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int updateDeleteTime(Long requestId, LocalDateTime localDateTime) {
        return em.createQuery("update BranchRequest br set br.deletedDate = :localDateTime where br.id = :requestId and br.deletedDate is null")
                .setParameter("localDateTime", localDateTime)
                .setParameter("requestId", requestId)
                .executeUpdate();
    }

    @Override
    public int updateAccept(Long requestId, AcceptType acceptType, LocalDateTime localDateTime) {
        return em.createQuery("update BranchRequest br set br.acceptType = :acceptType, br.updatedDate = :localDateTime ,br.deletedDate = :localDateTime where br.id = :requestId and br.updatedDate is null and br.deletedDate is null")
                .setParameter("localDateTime", localDateTime)
                .setParameter("requestId", requestId)
                .setParameter("acceptType", acceptType)
                .executeUpdate();
    }
}
