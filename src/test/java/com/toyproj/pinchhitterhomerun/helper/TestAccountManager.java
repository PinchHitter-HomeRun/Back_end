package com.toyproj.pinchhitterhomerun.helper;

import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.entity.MemberPasswordHint;
import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.repository.*;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
    BranchRequestRepository branchRequestRepository;

    @Autowired
    BranchRepository branchRepository;

    public static Member testMember;

    public void process() {
        testMember = memberRepository.findByLoginId("testAccount@daeta.com");

        if (testMember == null) {
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
                    throw new TestAccountManagerException("TestAccount SetHint Failed");
                }
            } else {
                throw new TestAccountManagerException("TestAccount Create Failed");
            }
        } else {
            leaveTestAccount();
            process();
        }
    }

    public void setBranch() {
        final var branch = branchRepository.findById(1L);
        final var updateMember = memberRepository.updateBranch(testMember.getId(), branch);

        if (updateMember == 0) {
            throw new TestAccountManagerException("TestAccount Set Branch Failed");
        }

        testMember = memberRepository.findById(testMember.getId());
    }

    public void removeBranch() {
        final var updateMember = memberRepository.updateBranch(testMember.getId(), null);

        if (updateMember == 0) {
            throw new TestAccountManagerException("TestAccount Remove Branch Failed");
        }

        testMember = memberRepository.findById(testMember.getId());
    }

    public void leaveTestAccount() {
        LocalDateTime deleteTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // 지점 요청건이 있으면 삭제
        final var findRequest = branchRequestRepository.findByMemberId(testMember.getId());

        if (findRequest != null) {
            final var updatedRow = branchRequestRepository.updateDeleteTime(findRequest.getId(), deleteTime);

            if (updatedRow != 1) {
                throw new TestAccountManagerException("TestAccount Remove Request Failed");
            }
        }

        final var updatedRow = memberRepository.updateDeleteTime(testMember.getId(), deleteTime);

        if (updatedRow != 1) {
            throw new TestAccountManagerException("TestAccount Leave Failed");
        }
    }

    public boolean haveBranch() {
        return memberRepository.findById(testMember.getId()) != null;
    }

    public boolean haveRequest() {
        return branchRequestRepository.findByMemberId(testMember.getId()) != null;
    }

    public void cancelRequest() {
        LocalDateTime deleteTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();

        final var request = branchRequestRepository.findByMemberId(testMember.getId());

        branchRequestRepository.updateDeleteTime(request.getId(), deleteTime);
    }
}