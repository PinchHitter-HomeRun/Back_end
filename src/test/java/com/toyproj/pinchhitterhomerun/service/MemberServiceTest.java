package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BranchException;
import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.model.*;
import com.toyproj.pinchhitterhomerun.repository.*;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberPasswordHintRepository memberPasswordHintRepository;
    @Autowired
    BranchRequestService branchRequestService;
    @Autowired
    BranchService branchService;

    @BeforeEach
    @Test
    public void 회원_가입_지점_없음() {
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

        Member findMember = memberService.getMemberInfo(joinedMember.getId());

        Assertions.assertThat(joinedMember.getId()).isEqualTo(findMember.getId());
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

        List<BranchRequest> findRequest = branchRequestService.getBranchRequest(givenMember.getBranchId());

        for (BranchRequest branchRequest : findRequest) {
            if (branchRequest.getMemberId().equals(joinedMember.getId())) {
                assertTrue(true);
                return;
            }
        }

        fail();
    }

    @Test
    public void 중복_체크_중복() {
        MemberException e = assertThrows(MemberException.class,
                () -> memberService.isAvailable("ojang@naver.com"));
        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.MEMBER_ID_ALREADY_USED.getMessage());
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
        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.MEMBER_LOGIN_FAILED.getMessage());
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
        memberService.leave(signedMember.getId());
        Assertions.assertThat(memberService.getMemberInfo(signedMember.getId()).getDeletedDate()).isNotNull();
    }

    @Test
    public void 탈퇴_사용자_로그인() {
        Member signedMember = memberService.signIn("ojang@naver.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9");
        memberService.leave(signedMember.getId());
        MemberException e = assertThrows(MemberException.class,
                () -> memberService.signIn("ojang@naver.com.com", "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9"));
        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.MEMBER_LOGIN_FAILED.getMessage());
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
        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
    }

    @Test
    public void 사용자가_속한_지점_가져오기_지점있음() {
        String branchName= "역삼초교사거리점";

        Branch branch = memberService.getMemberBranch(543L);

        Assertions.assertThat(branch.getName()).isEqualTo(branchName);
    }

    @Test
    public void 사용자가_속한_지점_가져오기_지점없음() {
        MemberException e = assertThrows(MemberException.class,
                () -> memberService.getMemberBranch(7L));
        Assertions.assertThat(e.getMessage()).isEqualTo("해당 회원이 속한 지점이 없습니다.");
    }
}