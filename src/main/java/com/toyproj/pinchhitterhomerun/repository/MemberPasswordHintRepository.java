package com.toyproj.pinchhitterhomerun.repository;

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
    public MemberPasswordHint findByMemberId(String loginId, String birthDay) {
        try {
            return em.createQuery("select ph from MemberPasswordHint ph where ph.memberId.loginId = :loginId and ph.memberId.birthDay = :birthDay", MemberPasswordHint.class)
                    .setParameter("loginId", loginId)
                    .setParameter("birthDay", birthDay)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
