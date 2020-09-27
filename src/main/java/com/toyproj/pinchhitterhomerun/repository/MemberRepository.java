package com.toyproj.pinchhitterhomerun.repository;


import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IMemberRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class MemberRepository implements IMemberRepository {

    private final EntityManager em;

    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public Member findByLoginId(String loginId) {
        return em.createQuery("select m from Member m where m.loginId = :loginId and m.deletedDate is null", Member.class)
                .setParameter("loginId", loginId)
                .getSingleResult();
    }

    @Override
    public Member findByLoginId(String loginId, String passWord) {

        return em.createQuery("select m from Member m where m.loginId = :loginId and m.passWord = :passWord and m.deletedDate is null", Member.class)
                .setParameter("loginId", loginId)
                .setParameter("passWord", passWord)
                .getSingleResult();
    }

    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }
}
