package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.entity.MemberPasswordHint;
import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.repository.*;
import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestAccountManager {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordHintRepository passwordHintRepository;

    @Autowired
    MemberPasswordHintRepository memberPasswordHintRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BranchRepository branchRepository;

    static Member testMember;

    public void process() {
        final var findTestMember = memberRepository.findByLoginId("testAccount@daeta.com");

        if (findTestMember == null) {
            final var createMember = Member.builder()
                    .birthDay("930903")
                    .branch(null)
                    .loginId("testAccount@daeta.com")
                    .name("테스트계정")
                    .passWord("30b4559e8f0435b14b9eca6b98d33f28d9fdd42ecd1ef1f5094470b592752d2a")
                    .phone("01012345678")
                    .role(roleRepository.findById(1L))
                    .sns(SnsType.None)
                    .sex(SexType.Male)
                    .build();

            final var hint = passwordHintRepository.findById(1L);

            if (memberRepository.save(createMember)) {
                testMember = memberRepository.findByLoginId("testAccount@daeta.com");

                final var memberPasswordHint = new MemberPasswordHint(testMember, hint, "답변");
                if (!memberPasswordHintRepository.save(memberPasswordHint)) {
                    throw new MemberException("TestAccount SetHint Failed");
                }
            } else {
                throw new MemberException("TestAccount Create Failed");
            }
        } else {
            testMember = findTestMember;
        }
    }

    public void setBranch() {
        final var branch = branchRepository.findById(1L);
        final var updateMember = memberRepository.updateBranch(testMember.getId(), branch);

        if (updateMember == 0) {
            throw new MemberException("TestAccount Set Branch Failed");
        }

        testMember = memberRepository.findById(testMember.getId());
    }

    public void removeBranch() {
        final var updateMember = memberRepository.updateBranch(testMember.getId(), null);

        if (updateMember == 0) {
            throw new MemberException("TestAccount Remove Branch Failed");
        }

        testMember = memberRepository.findById(testMember.getId());
    }

    public void leaveTestAccount() {
        testMember.updateDeletedDate();

        if (memberRepository.save(testMember)) {
            throw new MemberException("TestAccount Leave Failed");
        }
    }
}