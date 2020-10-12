package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.MemberException;
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
                "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9",
                SnsType.None,
                "홍길동",
                "930903",
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
    public void 중복_체크_중복() {
        MemberException e = assertThrows(MemberException.class,
                () -> memberService.isAvailable("ojang@naver.com"));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 사용중인 아이디입니다.");
    }

    @Test
    public void 중복_체크_사용가능() throws MemberException {
        boolean result = memberService.isAvailable("ojang1@naver.com");
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    public void 로그인_성공() throws MemberException {
        Member signedMember = memberService.signIn("ojang@naver.com","7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        Assertions.assertThat(signedMember.getName()).isEqualTo("홍길동");
    }

    @Test
    public void 로그인_실패() {
        MemberException e = assertThrows(MemberException.class,
                () -> memberService.signIn("ojang@naver.com", "ojang!!"));
        Assertions.assertThat(e.getMessage()).isEqualTo("아이디 혹은 비밀번호가 잘못 되었습니다.");
    }

    @Test
    public void 힌트_매핑() throws MemberException {
        Member signedMember = memberService.signIn("ojang@naver.com","7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        MemberPasswordHint memberPasswordHint = memberPasswordHintRepository.findByMemberId(signedMember.getId());
        Assertions.assertThat(memberPasswordHint.getHintId().getId()).isEqualTo(1L);
    }

    @Test
    public void 힌트_가져오기() throws MemberException {
        Member signedMember = memberService.signIn("ojang@naver.com","7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        MemberPasswordHint memberPasswordHint = memberPasswordHintRepository.findByMemberId(signedMember.getId());
        Assertions.assertThat(memberPasswordHint.getHintId().getText()).isEqualTo("none");
    }

    @Test
    public void 힌트_답변_매핑() throws MemberException {
        Member signedMember = memberService.signIn("ojang@naver.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        String answer = memberService.getHintAnswer(signedMember.getId());
        Assertions.assertThat(answer).isEqualTo("답변");
    }

    @Test
    public void 탈퇴() throws MemberException {
        Member signedMember = memberService.signIn("ojang@naver.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        Member leaveMember = memberRepository.findById(signedMember.getId());
        leaveMember.updateDeletedDate();
        Assertions.assertThat(memberRepository.findById(signedMember.getId()).getDeletedDate()).isNotNull();
    }

    @Test
    public void 탈퇴_사용자_로그인() throws MemberException {
        Member signedMember = memberService.signIn("ojang@naver.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        Member leaveMember = memberRepository.findById(signedMember.getId());
        leaveMember.updateDeletedDate();
        MemberException e = assertThrows(MemberException.class,
                () -> memberService.signIn("ojang@naver.com.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9"));
        Assertions.assertThat(e.getMessage()).isEqualTo("아이디 혹은 비밀번호가 잘못 되었습니다.");
    }

    @Test
    public void 비밀번호_변경() throws MemberException {
        memberService.updatePassword("ojang@naver.com", "qwer1234!!");
        Member signedMember = memberService.signIn("ohjang@daeta.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        Assertions.assertThat(signedMember).isNotNull();
    }

    @Test
    public void 아이디_찾기_성공() {
        String foundId = memberService.findLoginId("홍길동", "930903");
        Assertions.assertThat(foundId).isEqualTo("ojang@naver.com");
    }

    @Test
    public void 아이디_찾기_실패() {
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.findLoginId("오장원", "930911"));
        Assertions.assertThat(e.getMessage()).isEqualTo("존재하지 않는 사용자 입니다.");
    }
}