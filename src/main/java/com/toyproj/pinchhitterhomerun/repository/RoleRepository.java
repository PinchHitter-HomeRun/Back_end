package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.model.Role;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IRoleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleRepository implements IRoleRepository {

    private final EntityManager em;

    public RoleRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Role findByRoleName(String roleName) {
        return em.createQuery("select r from Role r where r.name = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getSingleResult();
    }

    @Override
    public Role findById(Long id) {
        return em.find(Role.class, id);
    }
}
