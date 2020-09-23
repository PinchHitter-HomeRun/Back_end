package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.repository.MemberPasswordHintRepository;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberPasswordHintRepository memberPasswordHintRepository;

    public MemberService(MemberRepository memberRepository, MemberPasswordHintRepository memberPasswordHintRepository) {
        this.memberRepository = memberRepository;
        this.memberPasswordHintRepository = memberPasswordHintRepository;
    }

    public Member join(@RequestBody Member member) {
        memberRepository.save(member);
        return member;
    }

    public boolean checkDuplicate(String loginId) {
        try {
            memberRepository.findByLoginId(loginId);
        } catch (Exception e) {
            return true;
        }
        throw new IllegalStateException("이미 사용중인 아이디입니다.");
    }

    public String getHintAnswer(Long memberId) {
        return memberPasswordHintRepository.findByMemberId(memberId).getAnswer();
    }

    public Member signIn(String loginId, String passWord){
        try {
            return memberRepository.findByLoginId(loginId, passWord);
        } catch (Exception e) {
            throw new IllegalStateException("아이디 혹은 비밀번호가 잘못 되었습니다.");
        }
    }
}
