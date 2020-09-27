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
                "ojang@naver.com",
                "ojang1234!!",
                SnsType.None,
                "오장원",
                930903,
                SexType.Male,
                "01012345678",
                1,
                roleRepository.findByRoleName("employee"),
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
                () -> memberService.isAvailable("ojang@naver.com"));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 사용중인 아이디입니다.");
    }

    @Test
    public void 중복_체크_사용가능() {
        boolean result = memberService.isAvailable("ojang1@naver.com");
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    public void 로그인_성공() {
        Member signedMember = memberService.signIn("ohjang@daeta.com","7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        Assertions.assertThat(signedMember.getName()).isEqualTo("오장원");
    }

    @Test
    public void 로그인_실패() {
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.signIn("ohjang@daeta.com", "ojang!!"));
        Assertions.assertThat(e.getMessage()).isEqualTo("아이디 혹은 비밀번호가 잘못 되었습니다.");
    }

    @Test
    public void 힌트_매핑() {
        Member signedMember = memberService.signIn("ohjang@daeta.com","7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        MemberPasswordHint memberPasswordHint = memberPasswordHintRepository.findByMemberId(signedMember.getId());
        Assertions.assertThat(memberPasswordHint.getHintId().getId()).isEqualTo(1L);
    }

    @Test
    public void 힌트_가져오기() {
        Member signedMember = memberService.signIn("ohjang@daeta.com","7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        MemberPasswordHint memberPasswordHint = memberPasswordHintRepository.findByMemberId(signedMember.getId());
        Assertions.assertThat(memberPasswordHint.getHintId().getText()).isEqualTo("none");
    }

    @Test
    public void 힌트_답변_매핑() {
        Member signedMember = memberService.signIn("ohjang@daeta.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        String answer = memberService.getHintAnswer(signedMember.getId());
        Assertions.assertThat(answer).isEqualTo("안녕");
    }

    @Test
    public void 탈퇴() {
        Member signedMember = memberService.signIn("ohjang@daeta.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        Member leaveMember = memberRepository.findById(signedMember.getId());
        leaveMember.updateDeletedDate();
        Assertions.assertThat(memberRepository.findById(signedMember.getId()).getDeletedDate()).isNotNull();
    }

    @Test
    public void 탈퇴_사용자_로그인() {
        Member signedMember = memberService.signIn("ohjang@daeta.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        Member leaveMember = memberRepository.findById(signedMember.getId());
        leaveMember.updateDeletedDate();
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.signIn("ohjang@daeta.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9"));
        Assertions.assertThat(e.getMessage()).isEqualTo("아이디 혹은 비밀번호가 잘못 되었습니다.");
    }

    @Test
    public void 비밀번호_변경() {
        memberService.updatePassword("ohjang@daeta.com", "qwer1234!!");
        Member signedMember = memberService.signIn("ohjang@daeta.com", "qwer1234!!");
        Assertions.assertThat(signedMember).isNotNull();
    }
}