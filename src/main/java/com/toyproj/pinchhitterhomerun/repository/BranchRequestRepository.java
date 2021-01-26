package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.entity.BranchRequest;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IBranchRequestRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
            return em.find(BranchRequest.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<BranchRequest> findByBranchId(Long branchId) {
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
}
