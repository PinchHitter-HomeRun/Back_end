package com.toyproj.pinchhitterhomerun.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class BranchRepository {

    private final EntityManager em;

    public BranchRepository(EntityManager em) {
        this.em = em;
    }
}
