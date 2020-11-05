package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.model.*;
import com.toyproj.pinchhitterhomerun.repository.*;
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
    MemberPasswordHintRepository memberPasswordHintRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    BranchRequestRepository branchRequestRepository;
    @Autowired
    BranchRequestService branchRequestService;


    @BeforeEach
    @Test
    public void 회원_가입_지점_없음() {

//        Member member = new Member(
//                "ojang@naver.com",
//                "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9",
//                SnsType.None,
//                "홍길동",
//                "930903",
//                SexType.Male,
//                "01012345678",
//                null,
//                roleRepository.findByRoleName("employee"),
//                null
//        );

        MemberJoin givenMember = MemberJoin.builder()
                .loginId("ojang@naver.com")
                .passWord("7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9")
                .sns(SnsType.None)
                .name("홍길동")
                .birthDay("930903")
                .sex(SexType.Male)
                .phone("01012345678")
                .branchId(null)
                .roleName("employee")
                .hintId(1L)
                .answer("안녕")
                .build();

        Member joinedMember = memberService.join(givenMember);

        Member findMember = memberRepository.findById(joinedMember.getId());
        Assertions.assertThat(findMember).isEqualTo(joinedMember);
    }

    @Test
    public void 회원_가입_지점_있음() {
        MemberJoin givenMember = MemberJoin.builder()
                .loginId("ojang@ddddd.com")
                .passWord("7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9")
                .sns(SnsType.None)
                .name("홍길동")
                .birthDay("930903")
                .sex(SexType.Male)
                .phone("01012345678")
                .branchId(1L)
                .roleName("employee")
                .hintId(1L)
                .answer("안녕")
                .build();

        Member joinedMember = memberService.join(givenMember);

        BranchRequest request = new BranchRequest(joinedMember.getId(), givenMember.getBranchId());

        BranchRequest findRequest = branchRequestRepository.findByMemberId(joinedMember.getId());

        Assertions.assertThat(findRequest.getMemberId()).isEqualTo(joinedMember.getId());
    }

    @Test
    public void 중복_체크_중복() {
        MemberException e = assertThrows(MemberException.class,
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
        Member signedMember = memberService.signIn("ojang@naver.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        Assertions.assertThat(signedMember.getName()).isEqualTo("홍길동");
    }

    @Test
    public void 로그인_실패() {
        MemberException e = assertThrows(MemberException.class,
                () -> memberService.signIn("ojang@naver.com", "ojang!!"));
        Assertions.assertThat(e.getMessage()).isEqualTo("아이디 혹은 비밀번호가 잘못 되었습니다.");
    }

    @Test
    public void 힌트_매핑() {
        Member signedMember = memberService.signIn("ojang@naver.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        MemberPasswordHint memberPasswordHint = memberPasswordHintRepository.findByMemberId(signedMember.getId());
        Assertions.assertThat(memberPasswordHint.getHintId().getId()).isEqualTo(1L);
    }

    @Test
    public void 힌트_가져오기() {
        Member signedMember = memberService.signIn("ojang@naver.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        MemberPasswordHint memberPasswordHint = memberPasswordHintRepository.findByMemberId(signedMember.getId());
        Assertions.assertThat(memberPasswordHint.getHintId().getText()).isEqualTo("none");
    }

    @Test
    public void 힌트_답변_매핑_성공() {
        Member signedMember = memberService.signIn("ojang@naver.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        boolean answer = memberService.matchHintAnswer(signedMember.getId(), "안녕");
        Assertions.assertThat(answer).isTrue();
    }

    @Test
    public void 힌트_답변_매핑_실패() {
        Member signedMember = memberService.signIn("ojang@naver.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        boolean answer = memberService.matchHintAnswer(signedMember.getId(), "실패");
        Assertions.assertThat(answer).isFalse();
    }

    @Test
    public void 탈퇴() throws MemberException {
        Member signedMember = memberService.signIn("ojang@naver.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        Member leaveMember = memberRepository.findById(signedMember.getId());
        leaveMember.updateDeletedDate();
        Assertions.assertThat(memberRepository.findById(signedMember.getId()).getDeletedDate()).isNotNull();
    }

    @Test
    public void 탈퇴_사용자_로그인() {
        Member signedMember = memberService.signIn("ojang@naver.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        Member leaveMember = memberRepository.findById(signedMember.getId());
        leaveMember.updateDeletedDate();
        MemberException e = assertThrows(MemberException.class,
                () -> memberService.signIn("ojang@naver.com.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9"));
        Assertions.assertThat(e.getMessage()).isEqualTo("아이디 혹은 비밀번호가 잘못 되었습니다.");
    }

    @Test
    public void 비밀번호_변경() {
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
        MemberException e = assertThrows(MemberException.class, () -> memberService.findLoginId("오장원", "930911"));
        Assertions.assertThat(e.getMessage()).isEqualTo("존재하지 않는 사용자 입니다.");
    }
}