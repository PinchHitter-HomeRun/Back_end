package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.entity.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class LoggerRepository {

    private final EntityManager em;

    public LoggerRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Logger logger) {
        em.persist(logger);
    }
}
