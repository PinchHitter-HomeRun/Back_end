package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.*;
import com.toyproj.pinchhitterhomerun.repository.*;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
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
    BranchRequestService branchRequestService;

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

        final var hint = passwordHintRepository.findById(newMember.getHintId());

        if (hint == null) {
            return new ServiceResult<>(ErrorMessage.HINT_NOT_EXIST);
        }

        final var memberPasswordHint = new MemberPasswordHint(member, hint, newMember.getAnswer());

        if (!memberRepository.save(member)) {
            return new ServiceResult<>(ErrorMessage.MEMBER_DB_FAIL);
        }

        if (!memberPasswordHintRepository.save(memberPasswordHint)) {
            return new ServiceResult<>(ErrorMessage.MEMBER_HINT_DB_FAIL);
        }

        if (newMember.getBranchId() != null) {
            final var request = new BranchRequest(member.getId(), newMember.getBranchId());
            branchRequestService.requestToBranchMaster(request);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, member);
    }

    /**
     * 중복체크
     */
    public ServiceResult<Boolean> isAvailable(String loginId) {
        final var result = memberRepository.findByLoginId(loginId);
        System.out.println("리절트 : " + result);
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

        final var updateRow = memberRepository.updateLastLoginDate(signMember.getId(), LocalDateTime.now());

        if (updateRow == 0) {
            return new ServiceResult<>(ErrorMessage.MEMBER_DB_FAIL);
        }

        signMember = memberRepository.findById(signMember.getId());

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

        updateMember.setPassWord(passWord);

        return new ServiceResult<>(ErrorMessage.SUCCESS, updateMember);
    }

    /**
     * 탈퇴
     */
    public ServiceResult<Member> leave(Long memberId) {
        final var leaveMember = memberRepository.findById(memberId);

        if (leaveMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
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
            return new ServiceResult<>(ErrorMessage.BRANCH_NOT_EXIST);
        }

        final var updateMember = memberRepository.updateBranch(memberId, branch);

        if (updateMember == 0) {
            return new ServiceResult<>(ErrorMessage.MEMBER_DB_FAIL);
        }

        final var member = memberRepository.findById(memberId);

        return new ServiceResult<>(ErrorMessage.SUCCESS, member);
    }
}
