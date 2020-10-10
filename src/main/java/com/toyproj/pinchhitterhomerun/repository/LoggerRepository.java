package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.model.Logger;
import com.toyproj.pinchhitterhomerun.model.MemberPasswordHint;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
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
