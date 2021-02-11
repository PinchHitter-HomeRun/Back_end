package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.entity.MemberPasswordHint;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IMemberPassWordHintRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class MemberPasswordHintRepository implements IMemberPassWordHintRepository {

    private final EntityManager em;

    public MemberPasswordHintRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean save(MemberPasswordHint memberPasswordHint) {
        try {
            em.persist(memberPasswordHint);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public MemberPasswordHint findById(Long id) {
        try {
            return em.find(MemberPasswordHint.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public MemberPasswordHint findByMember(Member member) {
        try {
            return em.createQuery("select ph from MemberPasswordHint ph where ph.memberId = :member", MemberPasswordHint.class)
                    .setParameter("member", member)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
