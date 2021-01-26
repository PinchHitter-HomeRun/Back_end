package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.*;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    BranchRequestService branchRequestService;

    @Autowired
    BranchService branchService;

    @Autowired
    TestAccountManager testAccountManager;
    
    static String originPassWord;
    static boolean initialized = false;

    @BeforeEach
    public void clean() {
        if (!initialized) {
            testAccountManager.process();
            originPassWord = TestAccountManager.testMember.getPassWord();
            initialized = true;
        }

        if (!TestAccountManager.testMember.getPassWord().equals(originPassWord)) {
            memberService.updatePassword(TestAccountManager.testMember.getId(), originPassWord);
        }
    }

    @Test
    @Transactional
    public void 회원_가입_지점_없음() {
        // given
        final var givenMember = MemberJoin.builder()
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

        // when
        final var result = memberService.join(givenMember);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var findMember = memberService.getMemberInfo(result.getResponse().getId());
        assertThat(result.getResponse().getLoginId()).isEqualTo(findMember.getResponse().getLoginId());
    }

    @Test
    @Transactional
    public void 회원_가입_지점_있음() {
        // given
        final var givenMember = MemberJoin.builder()
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

        // when
        final var result = memberService.join(givenMember);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var findRequest = branchRequestService.getBranchRequest(givenMember.getBranchId());

        for (BranchRequest branchRequest : findRequest) {
            if (branchRequest.getMemberId().equals(result.getResponse().getId())) {
                assertTrue(true);
                return;
            }
        }

        fail();
    }

    @Test
    public void 중복_체크_중복() {
        // given
        final var loginId = TestAccountManager.testMember.getLoginId();

        // when
        final var result = memberService.isAvailable(loginId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.MEMBER_ID_ALREADY_USED.getMessage());
    }

    @Test
    public void 중복_체크_사용가능() {
        // given
        final var loginId = "test";

        // when
        final var result = memberService.isAvailable(loginId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse()).isEqualTo(true);
    }

    @Test
    public void 로그인_성공() {
        // given
        final var loginId = TestAccountManager.testMember.getLoginId();
        final var passWord = TestAccountManager.testMember.getPassWord();

        // when
        final var result = memberService.signIn(loginId, passWord);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().getLoginId()).isEqualTo(loginId);
        assertThat(result.getResponse().getDeletedDate()).isNull();
        assertThat(result.getResponse().getLastLoginDate()).isNotNull();
    }

    @Test
    public void 로그인_실패_아이디_잘못됨() {
        // given
        final var loginId = "wrongid";
        final var passWord = TestAccountManager.testMember.getPassWord();
        
        // when
        final var result = memberService.signIn(loginId, passWord);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.MEMBER_LOGIN_FAILED.getMessage());
    }

    @Test
    public void 로그인_실패_비밀번호_잘못됨() {
        // given
        final var loginId = TestAccountManager.testMember.getLoginId();
        final var passWord = "wrongPW";

        // when
        final var result = memberService.signIn(loginId, passWord);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.MEMBER_LOGIN_FAILED.getMessage());
    }

    @Test
    public void 탈퇴() {
        // given
        final var memberId = TestAccountManager.testMember.getId();
        initialized = false;

        // when
        final var result = memberService.leave(memberId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().getDeletedDate()).isNotNull();
    }

    @Test
    public void 탈퇴_사용자_로그인() {
        // given
        final var memberId = TestAccountManager.testMember.getId();
        final var loginId = TestAccountManager.testMember.getLoginId();
        final var passWord = TestAccountManager.testMember.getPassWord();
        final var leave = memberService.leave(memberId);
        assertThat(leave.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        initialized = false;

        // when
        final var result = memberService.signIn(loginId, passWord);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.MEMBER_LOGIN_FAILED.getMessage());
    }

    @Test
    public void 비밀번호_변경() {
        // given
        final var memberId = TestAccountManager.testMember.getId();
        final var newPassWord = "update_pass_word";

        // when
        final var result = memberService.updatePassword(memberId, newPassWord);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().getPassWord()).isEqualTo(newPassWord);
    }

    @Test
    public void 아이디_찾기_성공() {
        // given
        final var name = TestAccountManager.testMember.getName();
        final var birthDay = TestAccountManager.testMember.getBirthDay();

        // when
        final var result = memberService.findLoginId(name, birthDay);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse()).isEqualTo(TestAccountManager.testMember.getLoginId());
    }

    @Test
    public void 아이디_찾기_실패() {
        // given
        final var name = TestAccountManager.testMember.getName();
        final var birthDay = "881129";

        // when
        final var result = memberService.findLoginId(name, birthDay);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
    }

    @Test
    @Transactional
    public void 사용자가_속한_지점_가져오기_지점있음() {
        // given
        final var memberId = TestAccountManager.testMember.getId();

        testAccountManager.setBranch();

        // when
        final var result = memberService.getMemberBranch(memberId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var expectedBranch = TestAccountManager.testMember.getBranch().getName();
        assertThat(result.getResponse().getName()).isEqualTo(expectedBranch);
    }

    @Test
    public void 사용자가_속한_지점_가져오기_지점없음() {
        // given
        final var memberId = TestAccountManager.testMember.getId();

        // when
        final var result = memberService.getMemberBranch(memberId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.MEMBER_BRANCH_NOT_EXIST.getMessage());
    }

    @Test
    public void 정보_수정을_위해_비밀번호_체크_성공() {
        // given
        final var memberId = TestAccountManager.testMember.getId();
        final var passWord = TestAccountManager.testMember.getPassWord();

        // when
        final var result = memberService.checkPassword(memberId, passWord);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse()).isNotNull();
    }

    @Test
    public void 정보_수정을_위해_비밀번호_체크_실패() {
        // given
        final var memberId = TestAccountManager.testMember.getId();
        final var passWord = "wrongPassword";

        // when
        final var result = memberService.checkPassword(memberId, passWord);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.MEMBER_WRONG_PASSWORD.getMessage());
    }
}