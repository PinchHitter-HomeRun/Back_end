package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.*;
import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.repository.*;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import com.toyproj.pinchhitterhomerun.util.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordHintRepository passwordHintRepository;

    @Autowired
    MemberPasswordHintRepository memberPasswordHintRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BranchRequestRepository branchRequestRepository;

    @Autowired
    BoardRepository boardRepository;

    /**
     * 회원가입
     */
    public ServiceResult<Member> join(MemberJoin newMember) {
        final var member = new Member(
                newMember.getLoginId(),
                newMember.getPassWord(),
                newMember.getSns(),
                newMember.getName(),
                newMember.getBirthDay(),
                newMember.getSex(),
                newMember.getPhone(),
                null,
                roleRepository.findByRoleName(newMember.getRoleName())
        );

        final var checkDuplicate = memberRepository.findByLoginId(newMember.getLoginId());

        if (checkDuplicate != null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_ID_ALREADY_USED);
        }

        final var hint = passwordHintRepository.findById(newMember.getHintId());

        if (hint == null) {
            return new ServiceResult<>(ErrorMessage.HINT_NOT_EXIST);
        }

        final var memberPasswordHint = new MemberPasswordHint(member, hint, newMember.getAnswer());

        if (!memberRepository.save(member)) {
            throw new MemberException(ErrorMessage.MEMBER_DB_ERROR);
        }

        if (!memberPasswordHintRepository.save(memberPasswordHint)) {
            throw new MemberException(ErrorMessage.MEMBER_HINT_DB_ERROR);
        }

        if (newMember.getBranchId() != null) {
            final var findMember = memberRepository.findByLoginId(newMember.getLoginId());

            if (findMember == null) {
                throw new MemberException(ErrorMessage.MEMBER_NOT_EXIST);
            }

            final var branchRequest = new BranchRequest(findMember.getId(), newMember.getBranchId());

            if (!branchRequestRepository.save(branchRequest)) {
                throw new MemberException(ErrorMessage.REQUEST_DB_ERROR);
            }
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, member);
    }

    /**
     * 중복체크
     */
    public ServiceResult<Boolean> isAvailable(String loginId) {
        final var result = memberRepository.findByLoginId(loginId);

        if (result != null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_ID_ALREADY_USED);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, true);
    }

    /**
     * 로그인
     */
    public ServiceResult<Member> signIn(String loginId, String passWord) {
        Member signMember;

        if (passWord != null) {
            signMember = memberRepository.findByLoginId(loginId, passWord);
        } else {
            signMember = memberRepository.findByLoginId(loginId); // sns 로그인
        }

        if (signMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_LOGIN_FAILED);
        }

        signMember.updateLastLoginDate();

        return new ServiceResult<>(ErrorMessage.SUCCESS, signMember);
    }

    /**
     * 멤버 정보
     */
    public ServiceResult<Member> getMemberInfo(Long memberId) {
        final Member findMember = memberRepository.findById(memberId);

        if (findMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, findMember);
    }

    /**
     * 비밀번호 수정
     */
    public ServiceResult<Member> updatePassword(Long memberId, String passWord) {
        final var updateMember = memberRepository.findById(memberId);

        if (updateMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        updateMember.updatePassWord(passWord);

        return new ServiceResult<>(ErrorMessage.SUCCESS, updateMember);
    }

    /**
     * 탈퇴
     */
    public ServiceResult<Member> leave(Long memberId) {
        final var leaveMember = memberRepository.findById(memberId);
        final var deleteTime = TimeManager.now();

        if (leaveMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        // 지점 요청건이 있으면 삭제
        final var findRequest = branchRequestRepository.findByMemberId(memberId);

        if (findRequest != null) {
            final var updatedRow = branchRequestRepository.updateDeleteTime(findRequest.getId(), deleteTime);

            if (updatedRow != 1) {
                throw new MemberException(ErrorMessage.REQUEST_DB_ERROR);
            }
        }

        // 작성한 게시글이 있으면 삭제
        final var memberBoard = boardRepository.findByMember(leaveMember);

        if (!memberBoard.isEmpty()) {
            final var deleteBoard = boardRepository.deleteAll(leaveMember, deleteTime);

            if (deleteBoard == 0) {
               throw new MemberException(ErrorMessage.BOARD_DB_ERROR);
            }
        }

        leaveMember.updateDeletedDate();

        return new ServiceResult<>(ErrorMessage.SUCCESS, leaveMember);
    }

    /**
     * 이름과 생년월일로 로그인 아이디 찾기
     */
    public ServiceResult<String> findLoginId(String name, String birthDay) {
        final var foundMember = memberRepository.findLoginIdByInfo(name, birthDay);

        if (foundMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, foundMember.getLoginId());
    }

    /**
     * 사용자가 속한 지점 검색
     */
    public ServiceResult<Branch> getMemberBranch(Long memberId) {
        final var member = memberRepository.findById(memberId);

        if (member == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        if (member.getBranch() == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_BRANCH_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, member.getBranch());
    }

    /**
     * 정보 수정을 위한 현재 비밀번호 확인
     */
    public ServiceResult<Member> checkPassword(Long memberId, String passWord) {
        final var member = memberRepository.findById(memberId);

        if (member == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        final var memberPassWord = member.getPassWord();

        if (!memberPassWord.equals(passWord)) {
            return new ServiceResult<>(ErrorMessage.MEMBER_WRONG_PASSWORD);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, member);
    }

    /**
     * 멤버에 지점 세팅
     */
    public ServiceResult<Member> setBranch(Long memberId, Long branchId) {
        final var memberInfo = memberRepository.findById(memberId);

        if (memberInfo == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        if (memberInfo.getBranch() != null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_BRANCH_ALREADY_EXIST);
        }

        final var branch = branchRepository.findById(branchId);

        if (branch == null) {
            throw new MemberException(ErrorMessage.BRANCH_NOT_EXIST);
        }

        memberInfo.updateBranch(branch);

        final var member = memberRepository.findById(memberId);

        return new ServiceResult<>(ErrorMessage.SUCCESS, member);
    }

    /**
     * 멤버 지점에서 탈퇴
     */
    public ServiceResult<Member> leaveBranch(Long memberId) {
        final var memberInfo = memberRepository.findById(memberId);

        if (memberInfo == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        if (memberInfo.getBranch() == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_BRANCH_NOT_EXIST);
        }

        memberInfo.updateBranch(null);

        final var afterMemberInfo = memberRepository.findById(memberId);

        if (afterMemberInfo == null) {
            throw new MemberException(ErrorMessage.MEMBER_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, afterMemberInfo);
    }
}
