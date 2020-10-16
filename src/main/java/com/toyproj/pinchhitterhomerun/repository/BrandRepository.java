package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.model.Brand;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IBrandRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class BrandRepository implements IBrandRepository {

    private final EntityManager em;

    public BrandRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Brand> findAll() {
        return em.createQuery("select b from Brand b", Brand.class)
                .getResultList();
    }

    @Override
    public List<Brand> findByCategoryId(Long categoryId) {
        return em.createQuery("select b from Brand b where b.category.id = :categoryId", Brand.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public Brand findByName(String name) {
        return em.createQuery("select b from Brand b where b.name = :name",Brand.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public Brand findById(Long id) {
        return em.createQuery("select b from Brand b where b.id = :id", Brand.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
