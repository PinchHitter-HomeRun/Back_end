package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.model.Address;
import com.toyproj.pinchhitterhomerun.model.Branch;
import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IBranchRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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
    public List<Branch> searchByKeywordWithBrandId(Long brandId, String city, String gu, String branchNameOrNull) {
        if (branchNameOrNull == null) {
            return em.createQuery("select b from Branch b where b.brand.id = :brandId and b.address.city = :city and b.address.gu = :gu", Branch.class)
                    .setParameter("brandId", brandId)
                    .setParameter("city", city)
                    .setParameter("gu", gu)
                    .getResultList();
        }

        return em.createQuery("select b from Branch b where b.brand.id = :brandId and b.address.city = :city and b.address.gu = :gu and b.name = :branchName", Branch.class)
                .setParameter("brandId", brandId)
                .setParameter("city", city)
                .setParameter("gu", gu)
                .setParameter("branchName", branchNameOrNull)
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
        return em.createQuery("select m.branch from Member m where m.id = :memberId", Branch.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }

    @Override
    public Branch findByName(String name) {
        return em.createQuery("select b from Branch b where b.name = :name", Branch.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public Branch findByAddress(String address) {
        List<Branch> branches = em.createQuery("select b from Branch b", Branch.class).getResultList();

        for (Branch branch : branches) {
            if (branch.getAddress().getFullAddress().equals(address)) {
                return branch;
            }
        }

        return null;
    }
}
