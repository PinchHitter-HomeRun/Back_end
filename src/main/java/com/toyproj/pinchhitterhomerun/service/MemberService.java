package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.model.MemberPasswordHint;
import com.toyproj.pinchhitterhomerun.model.PasswordHint;
import com.toyproj.pinchhitterhomerun.repository.MemberPasswordHintRepository;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import com.toyproj.pinchhitterhomerun.repository.PasswordHintRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordHintRepository passwordHintRepository;
    private final MemberPasswordHintRepository memberPasswordHintRepository;

    public MemberService(MemberRepository memberRepository, PasswordHintRepository passwordHintRepository,MemberPasswordHintRepository memberPasswordHintRepository) {
        this.memberRepository = memberRepository;
        this.passwordHintRepository = passwordHintRepository;
        this.memberPasswordHintRepository = memberPasswordHintRepository;
    }
    
    // 회원가입
    public Member join(Member member, Long hintId, String hintAnswer) {
        MemberPasswordHint memberPasswordHint = new MemberPasswordHint(member, passwordHintRepository.findById(hintId), hintAnswer);
        memberRepository.save(member);
        memberPasswordHintRepository.save(memberPasswordHint);
        return member;
    }

    // 중복체크
    public boolean isAvailable(String loginId) {
        try {
            memberRepository.findByLoginId(loginId);
        } catch (Exception e) {
            return true;
        }
        throw new IllegalStateException("이미 사용중인 아이디입니다.");
    }

    // 로그인
    public Member signIn(String loginId, String passWord){
        Member signMember;
        try {
            if (passWord != null) {
                signMember = memberRepository.findByLoginId(loginId, passWord);
            } else {
                signMember = memberRepository.findByLoginId(loginId); // sns 로그인
            }
            signMember.updateLastLoginDate();
        } catch (Exception e) {
            throw new IllegalStateException("아이디 혹은 비밀번호가 잘못 되었습니다.");
        }

        return signMember;
    }

    // 멤버 정보
    public Member getMemberInfo(Long memberId) {
        return memberRepository.findById(memberId);
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
}
