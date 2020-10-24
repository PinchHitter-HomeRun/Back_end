package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.repository.interfaces.IBranchRequestRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class BranchRequestRepository implements IBranchRequestRepository {

    private final EntityManager em;

    public BranchRequestRepository(EntityManager em) {
        this.em = em;
    }


}
