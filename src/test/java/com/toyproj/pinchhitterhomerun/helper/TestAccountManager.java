package com.toyproj.pinchhitterhomerun.helper;

import com.toyproj.pinchhitterhomerun.entity.*;
import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.repository.*;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import com.toyproj.pinchhitterhomerun.util.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;

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

    @Autowired
    BoardRepository boardRepository;

    public static Member testMember;
    public static Member subTestMember;

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

    public void setBranch(Branch branch) {
        testMember = memberRepository.findById(testMember.getId());
        testMember.updateBranch(branch);

        if(!memberRepository.save(testMember)) {
            throw new TestAccountManagerException("TestAccount Set Branch Failed");
        }
    }

    public void removeBranch() {
        testMember = memberRepository.findById(testMember.getId());
        testMember.updateBranch(null);

        if(!memberRepository.save(testMember)) {
            throw new TestAccountManagerException("TestAccount Remove Branch Failed");
        }
    }

    public void leaveTestAccount() {
        LocalDateTime deleteTime = TimeManager.now();

        // 지점 요청건이 있으면 삭제
        final var findRequest = branchRequestRepository.findByMemberId(testMember.getId());

        if (findRequest != null) {
            findRequest.delete();
        }

        // 작성한 게시글이 있으면 삭제
        final var memberBoard = boardRepository.findAllBoardByMember(testMember);

        if (!memberBoard.isEmpty()) {
            final var deleteBoard = boardRepository.deleteAll(testMember, deleteTime);

            if (deleteBoard == 0) {
                throw new MemberException(ErrorMessage.BOARD_DB_ERROR);
            }
        }

        memberRepository.updateDeletedDate(testMember.getId(), deleteTime);
    }

    public boolean haveBranch() {
        return memberRepository.findById(testMember.getId()).getBranch() != null;
    }

    public boolean haveRequest() {
        return branchRequestRepository.findByMemberId(testMember.getId()) != null;
    }

    public void cancelRequest() {
        LocalDateTime deleteTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();

        final var request = branchRequestRepository.findByMemberId(testMember.getId());

        branchRequestRepository.updateDeleteTime(request.getId(), deleteTime);
    }

    public void deleteAllBoard() {
        final var boards = boardRepository.findAllBoardByMember(testMember);

        if (!boards.isEmpty()) {
            boards.forEach(Board::updateDeleteTime);
        }
    }

    public Member newMember() {
        final var createMember = Member.builder()
                .birthDay("971112")
                .branch(branchRepository.findById(1L))
                .loginId("testSubAccount@daeta.com")
                .name("테스트서브계정")
                .passWord("30b4559e8f0435b14b9eca6b98d33f28d9fdd42ecd1ef1f5094470b592752d2a")
                .phone("01098765432")
                .role(roleRepository.findById(1L))
                .sns(SnsType.None)
                .sex(SexType.Male)
                .build();

        final var hint = passwordHintRepository.findById(1L);

        if (memberRepository.save(createMember)) {
            subTestMember = memberRepository.findByLoginId(createMember.getLoginId());

            if (subTestMember == null) {
                throw new TestAccountManagerException("testSubAccount is null");
            }

            final var memberPasswordHint = new MemberPasswordHint(subTestMember, hint, "답변");
            if (!memberPasswordHintRepository.save(memberPasswordHint)) {
                throw new TestAccountManagerException("testSubAccount SetHint Failed");
            }
        } else {
            throw new TestAccountManagerException("testSubAccount Create Failed");
        }

        return createMember;
    }

    public void removeSubTestAccountBranch() {
        subTestMember = memberRepository.findById(subTestMember.getId());
        subTestMember.updateBranch(null);

        if(!memberRepository.save(subTestMember)) {
            throw new TestAccountManagerException("TestAccount Remove Branch Failed");
        }
    }

    public void leaveSubTestAccount() {
        subTestMember = memberRepository.findByLoginId("testSubAccount@daeta.com");

        if (subTestMember != null) {
            subTestMember.updateDeletedDate();
        }

        subTestMember = null;
    }

    public void setMaster() {
        final var master = roleRepository.findByRoleName("master");

        if (master == null) {
            throw new TestAccountManagerException("Failed to get master");
        }

        memberRepository.updateRole(testMember.getId(), master);
    }

    public void unSetMaster() {
        final var none = roleRepository.findByRoleName("none");

        if (none == null) {
            throw new TestAccountManagerException("Failed to get none");
        }

        testMember.setRole(none);
    }
}