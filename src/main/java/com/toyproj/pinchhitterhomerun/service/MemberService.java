package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.model.MemberPasswordHint;
import com.toyproj.pinchhitterhomerun.repository.MemberPasswordHintRepository;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberPasswordHintRepository memberPasswordHintRepository;

    public MemberService(MemberRepository memberRepository, MemberPasswordHintRepository memberPasswordHintRepository) {
        this.memberRepository = memberRepository;
        this.memberPasswordHintRepository = memberPasswordHintRepository;
    }

    public Member join(Member member, String hintAnswer) {
        MemberPasswordHint memberPasswordHint = new MemberPasswordHint(member, member.getPasswordHint(),hintAnswer);
        memberRepository.save(member);
        memberPasswordHintRepository.save(memberPasswordHint);
        return member;
    }

    public boolean isAvailable(String loginId) {
        try {
            memberRepository.findByLoginId(loginId);
        } catch (Exception e) {
            return true;
        }
        throw new IllegalStateException("이미 사용중인 아이디입니다.");
    }

    public Member signIn(String loginId, String passWord){
        try {
            Member signMember = memberRepository.findByLoginId(loginId, passWord);
            signMember.updateLastLoginDate();
            return signMember;
        } catch (Exception e) {
            throw new IllegalStateException("아이디 혹은 비밀번호가 잘못 되었습니다.");
        }
    }

    public Member getMemberInfo(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public String getHintAnswer(Long memberId) {
        return memberPasswordHintRepository.findByMemberId(memberId).getAnswer();
    }
}
