package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.model.BranchRequest;
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
    public void save(BranchRequest branchRequest) {
        em.persist(branchRequest);
    }

    @Override
    public BranchRequest findById(Long id) {
        return em.createQuery("select br from BranchRequest br where br.id = :id and br.deletedDate is null", BranchRequest.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<BranchRequest> findByBranchId(Long branchId) {
        return em.createQuery("select br from BranchRequest br where br.branchId = :branchId and br.deletedDate is null", BranchRequest.class)
                .setParameter("branchId", branchId)
                .getResultList();
    }

    @Override
    public BranchRequest findByMemberId(Long memberId) {
        return em.createQuery("select br from BranchRequest br where br.memberId = :memberId and br.deletedDate is null", BranchRequest.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}
