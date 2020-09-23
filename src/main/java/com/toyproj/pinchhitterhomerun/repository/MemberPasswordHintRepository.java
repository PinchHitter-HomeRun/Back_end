package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.model.MemberPasswordHint;
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
    public void save(MemberPasswordHint memberPasswordHint) {
        em.persist(memberPasswordHint);
    }

    @Override
    public MemberPasswordHint findByMemberId(Long memberId) {
        return em.createQuery("select ph from MemberPasswordHint ph where ph.memberId = :memberId", MemberPasswordHint.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}
