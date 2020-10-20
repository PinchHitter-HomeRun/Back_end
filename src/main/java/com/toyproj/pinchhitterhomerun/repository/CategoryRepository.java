package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.model.Category;
import com.toyproj.pinchhitterhomerun.repository.interfaces.ICategoryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CategoryRepository implements ICategoryRepository {

    private final EntityManager em;

    public CategoryRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }

    @Override
    public Category findByName(String name) {
        return em.createQuery("select c from Category c where c.name = :name", Category.class)
                .setParameter("name",name)
                .getSingleResult();
    }

    @Override
    public Category findById(Long id) {
        return em.find(Category.class, id);
    }
}
