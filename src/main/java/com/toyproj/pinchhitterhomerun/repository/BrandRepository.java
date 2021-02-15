package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Brand;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IBrandRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

@Repository
public class BrandRepository implements IBrandRepository {

    private final EntityManager em;

    public BrandRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Collection<Brand> findAll() {
        return em.createQuery("select b from Brand b", Brand.class)
                .getResultList();
    }

    @Override
    public Collection<Brand> findByCategoryId(Long categoryId) {
        return em.createQuery("select b from Brand b where b.category.id = :categoryId", Brand.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public Brand findByName(String name) {
        try {
            return em.createQuery("select b from Brand b where b.name = :name", Brand.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Brand findById(Long id) {
        try {
            return em.find(Brand.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}
