package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.model.MemberJoin;
import com.toyproj.pinchhitterhomerun.model.MemberPasswordHint;
import com.toyproj.pinchhitterhomerun.repository.MemberPasswordHintRepository;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import com.toyproj.pinchhitterhomerun.repository.PasswordHintRepository;
import com.toyproj.pinchhitterhomerun.repository.RoleRepository;
import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordHintRepository passwordHintRepository;
    @Autowired
    MemberPasswordHintRepository memberPasswordHintRepository;
    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    @Test
    public void 회원_가입() {

        Member member = new Member(
                "ojang",
                "ojang1234!!",
                SnsType.None,
                "오장원",
                930903,
                SexType.Male,
                "01012345678",
                1,
                roleRepository.findByRoleName("employee"),
                "qwe@naver.com",
                "서울시 우리구 우리동 우리아파트 우리동 우리호",
                null
        );
        MemberJoin newMember = new MemberJoin(member, 1L,"답변");

        Member joinedMember = memberService.join(newMember.getMember(), newMember.getHintId(), newMember.getAnswer());

        Member findMember = memberRepository.findById(joinedMember.getId());
        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 중복_체크_중복() throws Exception{
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.isAvailable("ojang"));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 사용중인 아이디입니다.");
    }

    @Test
    public void 중복_체크_사용가능() {
        boolean result = memberService.isAvailable("ojang1");
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    public void 로그인_성공() {
        Member signedMember = memberService.signIn("ojang","ojang1234!!");
        Assertions.assertThat(signedMember.getName()).isEqualTo("오장원");
    }

    @Test
    public void 로그인_실패() {
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.signIn("ojang", "ojang!!"));
        Assertions.assertThat(e.getMessage()).isEqualTo("아이디 혹은 비밀번호가 잘못 되었습니다.");
    }

    @Test
    public void 힌트_매핑() {
        Member signedMember = memberService.signIn("ojang","ojang1234!!");
        System.out.println(signedMember.getId());
        MemberPasswordHint memberPasswordHint = memberPasswordHintRepository.findByMemberId(signedMember.getId());
        System.out.println(memberPasswordHint.getAnswer());
        Assertions.assertThat(memberPasswordHint.getHintId().getId()).isEqualTo(1L);
    }
}