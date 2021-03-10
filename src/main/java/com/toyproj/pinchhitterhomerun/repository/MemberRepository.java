package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.entity.Role;
import com.toyproj.pinchhitterhomerun.repository.interfaces.IMemberRepository;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public class MemberRepository implements IMemberRepository {

    final EntityManager em;

    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean save(Member member) {
        try {
            em.persist(member);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public Member findByLoginId(String loginId) {
        try {
            return em.createQuery("select m from Member m where m.loginId = :loginId and m.sns = :sns and m.deletedDate is null", Member.class)
                    .setParameter("loginId", loginId)
                    .setParameter("sns", SnsType.None)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Member findByLoginId(String loginId, String passWord) {
        try {
            return em.createQuery("select m from Member m where m.loginId = :loginId and m.passWord = :passWord and m.deletedDate is null", Member.class)
                    .setParameter("loginId", loginId)
                    .setParameter("passWord", passWord)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Member findById(Long id) {
        try {
            return em.createQuery("select m from Member m where m.id = :id and m.deletedDate is null", Member.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Member findLoginIdByInfo(String name, String birthDay) {
        try {
            return em.createQuery("select m from Member m where m.name = :name and m.birthDay = :birthDay and m.deletedDate is null", Member.class)
                    .setParameter("name", name)
                    .setParameter("birthDay", birthDay)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Member findByLoginIdAndBirthDay(String loginId, String birthDay) {
        try {
            return em.createQuery("select m from Member m where m.loginId = :loginId and m.birthDay = :birthDay and m.deletedDate is null", Member.class)
                    .setParameter("loginId", loginId)
                    .setParameter("birthDay", birthDay)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Collection<Member> findByBranchId(Long branchId) {
        return em.createQuery("select m from Member m where m.branch.id = :branchId and m.deletedDate is null", Member.class)
                .setParameter("branchId", branchId)
                .getResultList();
    }

    @Override
    public Collection<Member> findByContainsName(String name) {
        return em.createQuery("select m from Member m where m.name like :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public int updateBranch(Long memberId, Branch branch) {
        final var updatedRow = em.createQuery("update Member m set m.branch = :branch where m.id = :memberId and m.deletedDate is null")
                .setParameter("branch", branch)
                .setParameter("memberId", memberId)
                .executeUpdate();

        em.flush();
        em.clear();

        return updatedRow;
    }

    @Override
    public int updateRole(Long memberId, Role role) {
        final var updatedRow = em.createQuery("update Member m set m.role = :role where m.id = :memberId and m.deletedDate is null")
                .setParameter("role", role)
                .setParameter("memberId", memberId)
                .executeUpdate();

        em.flush();
        em.clear();

        return updatedRow;
    }
}