package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.entity.PasswordHint;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IPasswordHintRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

@Repository
public class PasswordHintRepository implements IPasswordHintRepository {

    private final EntityManager em;

    public PasswordHintRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public PasswordHint findById(Long id) {
        return em.find(PasswordHint.class, id);
    }

    @Override
    public Collection<PasswordHint> findAll() {
        return em.createQuery("select ph from PasswordHint ph", PasswordHint.class)
                .getResultList();
    }
}
