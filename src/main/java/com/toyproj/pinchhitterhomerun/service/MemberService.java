package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.model.MemberJoin;
import com.toyproj.pinchhitterhomerun.model.MemberPasswordHint;
import com.toyproj.pinchhitterhomerun.model.PasswordHint;
import com.toyproj.pinchhitterhomerun.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordHintRepository passwordHintRepository;
    private final MemberPasswordHintRepository memberPasswordHintRepository;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;

    public MemberService(MemberRepository memberRepository, PasswordHintRepository passwordHintRepository, MemberPasswordHintRepository memberPasswordHintRepository, BranchRepository branchRepository, RoleRepository roleRepository) {
        this.memberRepository = memberRepository;
        this.passwordHintRepository = passwordHintRepository;
        this.memberPasswordHintRepository = memberPasswordHintRepository;
        this.branchRepository = branchRepository;
        this.roleRepository = roleRepository;
    }

    // 회원가입
    public Member join(MemberJoin newMember) {
        Member member = new Member(
                newMember.getLoginId(),
                newMember.getPassWord(),
                newMember.getSns(),
                newMember.getName(),
                newMember.getBirthDay(),
                newMember.getSex(),
                newMember.getPhone(),
                branchRepository.findById(newMember.getBranchId()),
                roleRepository.findByRoleName(newMember.getRoleName())
        );

        MemberPasswordHint memberPasswordHint = new MemberPasswordHint(member, passwordHintRepository.findById(newMember.getHintId()), newMember.getAnswer());
        memberRepository.save(member);
        memberPasswordHintRepository.save(memberPasswordHint);
        return member;
    }

    // 중복체크
    public boolean isAvailable(String loginId) throws MemberException{
        try {
            memberRepository.findByLoginId(loginId);
        } catch (Exception e) {
            return true;
        }

        throw new MemberException("이미 사용중인 아이디입니다.");
    }

    // 로그인
    public Member signIn(String loginId, String passWord) throws MemberException {
        Member signMember;
        try {
            if (passWord != null) {
                signMember = memberRepository.findByLoginId(loginId, passWord);
            } else {
                signMember = memberRepository.findByLoginId(loginId); // sns 로그인
            }
            signMember.updateLastLoginDate();
        } catch (Exception e) {
            throw new MemberException("아이디 혹은 비밀번호가 잘못 되었습니다.");
        }

        return signMember;
    }

    // 멤버 정보
    public Member getMemberInfo(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //힌트 리스트
    public List<PasswordHint> getAllHint() {
        return passwordHintRepository.findAll();
    }

    // 힌트 텍스트
    public PasswordHint getPasswordHint(Long memberId) {
        return memberPasswordHintRepository.findByMemberId(memberId).getHintId();
    }

    // 힌트 답변
    public String getHintAnswer(Long memberId) {
        return memberPasswordHintRepository.findByMemberId(memberId).getAnswer();
    }

    // 비밀번호 수정
    public Member updatePassword(String loginId, String passWord) {
        Member updateMember = memberRepository.findByLoginId(loginId);
        updateMember.setPassWord(passWord);
        return updateMember;
    }

    // 탈퇴
    public Member leave(Long memberId) {
        Member leaveMember = memberRepository.findById(memberId);
        leaveMember.updateDeletedDate();
        return leaveMember;
    }

    // 이름과 생년월일로 아이디 찾기
    public String findLoginId(String name, String birthDay) {
        Member foundMember;

        try {
            foundMember = memberRepository.findLoginIdByInfo(name, birthDay);
        } catch (Exception e) {
            throw new MemberException("존재하지 않는 사용자 입니다.");
        }

        return foundMember.getLoginId();
    }

    // 지점에 속한 모든 인원 찾기
    public List<Member> getBranchMembers(Long branchId) {
        List<Member> members;

        members = memberRepository.findByBranchId(branchId);

        if (members.size() == 0) {
            throw new MemberException("존재하지 않는 지점 입니다.");
        }

        return members;
    }

    //지점에 알바생 등록 신청
    public void requestToBranchMaster(Long memberId, Long branchId) {
        
    }

}
