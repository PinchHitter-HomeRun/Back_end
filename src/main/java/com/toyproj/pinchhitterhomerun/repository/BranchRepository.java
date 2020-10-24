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
        return em.find(Branch.class, id);
    }

    @Override
    public Branch findByBrandAndName(Long brandId, String name) {
        return em.createQuery("select b from Branch b where b.brand.id = :brandId and b.name = :name", Branch.class)
                .setParameter("brandId", brandId)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public Branch findByAddress(String address) {
        return em.createQuery("select b from Branch b where b.address = :address", Branch.class)
                .setParameter("address", address)
                .getSingleResult();
    }

    @Override
    public List<Branch> searchByKeywordWithBrandId(Long brandId, String city, String sub, String text) {
        return em.createQuery("select b from Branch b where b.brand.id = :brandId and b.address like :city and b.address like :sub and b.address like :text", Branch.class)
                .setParameter("brandId", brandId)
                .setParameter("city", "%" + city + "%")
                .setParameter("sub", "%" + sub + "%")
                .setParameter("text", "%" + text + "%")
                .getResultList();
    }

    @Override
    public List<Branch> findByBrandId(Long brandId) {
        return em.createQuery("select b from Branch b where b.brand.id = :brandId", Branch.class)
                .setParameter("brandId", brandId)
                .getResultList();
    }

    @Override
    public Branch findByMemberId(Long memberId) {
        return em.createQuery("select b from Branch b where b.id = (select m.branch.id from Member m where m.id = :memberId)", Branch.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}
