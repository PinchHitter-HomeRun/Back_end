package com.toyproj.pinchhitterhomerun.repository;


import com.toyproj.pinchhitterhomerun.model.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class MemberRepository implements IMemberRepository {

    @PersistenceContext
    EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

}
