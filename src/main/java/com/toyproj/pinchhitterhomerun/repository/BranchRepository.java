package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.model.Branch;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IBranchRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class BranchRepository implements IBranchRepository {

    private final EntityManager em;

    public BranchRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Branch findById(Long id) {
        if (id == null) {
            return null;
        }
        return em.find(Branch.class, id);
    }

    @Override
    public List<Branch> searchByName(String city, String sub, String text) {
        return null;
    }

    @Override
    public List<Branch> findByBrandId(Long brandId) {
        return null;
    }
}
