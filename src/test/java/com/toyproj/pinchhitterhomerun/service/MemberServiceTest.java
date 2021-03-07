package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.*;
import com.toyproj.pinchhitterhomerun.helper.TestAccountManager;
import com.toyproj.pinchhitterhomerun.helper.TestHelper;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberServiceTest extends TestHelper {

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
    @Rollback(value = true)
    public void 회원_가입_지점_없음() {
        // given
        final String loginId = "ojang@naver.com";
        final String passWord = "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9";
        final String name = "홍킬동";
        final SnsType snsType = SnsType.None;
        final String birthDay = "930903";
        final SexType sex = SexType.Male;
        final String phone = "01012345678";
        final Long hintId = 1L;
        final String answer = "안녕";

        // when
        final var result = memberService.join(
                loginId,
                passWord,
                snsType,
                name,
                birthDay,
                sex,
                phone,
                null,
                hintId,
                answer
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var findMember = memberService.getMemberInfo(result.getResponse().getId());
        assertThat(result.getResponse().getLoginId()).isEqualTo(findMember.getResponse().getLoginId());
    }

    @Test
    @Rollback(value = true)
    public void 회원_가입_지점_있음() {
        // given
        final String loginId = "ojang@naver.com";
        final String passWord = "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9";
        final String name = "홍킬동";
        final SnsType snsType = SnsType.None;
        final String birthDay = "930903";
        final SexType sex = SexType.Male;
        final String phone = "01012345678";
        final Long branchId = 1L;
        final Long hintId = 1L;
        final String answer = "안녕";

        // when
        final var result = memberService.join(
                loginId,
                passWord,
                snsType,
                name,
                birthDay,
                sex,
                phone,
                branchId,
                hintId,
                answer
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var findRequest = branchRequestService.getBranchRequest(branchId);
        assertThat(findRequest.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        for (BranchRequest branchRequest : findRequest.getResponse()) {
            if (branchRequest.getMemberId().equals(result.getResponse().getId())) {
                assertTrue(true);
                return;
            }
        }

        fail();
    }

    @Test
    @Rollback(value = true)
    public void 소셜_회원_가입() {
        // given
        final String loginId = "ojang@naver.com";
        final String passWord = "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9";
        final String name = "홍킬동";
        final SnsType snsType = SnsType.Kakao;
        final String birthDay = "930903";
        final SexType sex = SexType.Male;
        final String phone = "01012345678";
        final Long branchId = 1L;
        final Long hintId = 1L;
        final String answer = "안녕";

        // when
        final var result = memberService.join(
                loginId,
                passWord,
                snsType,
                name,
                birthDay,
                sex,
                phone,
                branchId,
                hintId,
                answer
        );

        // then
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
    public void 사용자가_속한_지점_가져오기_지점있음() {
        // given
        final var memberId = TestAccountManager.testMember.getId();

        testAccountManager.setBranch(getRandomBranch());

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

    @Test
    public void 지점에_속했을_때_지점에서_탈퇴하기() {
        // given
        testAccountManager.setBranch(getRandomBranch());
        final var memberId = TestAccountManager.testMember.getId();

        // when
        final var result = memberService.leaveBranch(memberId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var afterMemberInfo = memberService.getMemberInfo(memberId);
        assertThat(afterMemberInfo.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(afterMemberInfo.getResponse().getBranch()).isNull();
    }

    @Test
    public void 속한_지점이_없을_때_지점에서_탈퇴하기() {
        // given
        final var memberId = TestAccountManager.testMember.getId();

        // when
        final var result = memberService.leaveBranch(memberId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.MEMBER_BRANCH_NOT_EXIST.getMessage());
    }

    @Test
    @Rollback(value = true)
    public void 관리자_권한_부여() {
        // given
        final String loginId = "ojangAdmin@naver.com";
        final String passWord = "7387ECF02490D22F6E6D98A8F0C638D683778B9D329C5081CE4DCAF8BF2E59B9";
        final String name = "홍킬동";
        final SnsType snsType = SnsType.None;
        final String birthDay = "930903";
        final SexType sex = SexType.Male;
        final String phone = "01012345678";
        final Long hintId = 1L;
        final String answer = "안녕";

        final var join = memberService.join(
                loginId,
                passWord,
                snsType,
                name,
                birthDay,
                sex,
                phone,
                null,
                hintId,
                answer
        );
        assertThat(join.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = memberService.grantAdminPermission(loginId, true);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var afterMember = memberService.getMemberInfo(join.getResponse().getId());
        assertThat(afterMember.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(afterMember.getResponse().isAdmin()).isEqualTo(true);
    }

    @Test
    public void 존재하지_않는_사용자에게_관리자_권한_부여() {
        // given
        final var testMemberLoginId = "invalid@daeta.com";

        // when
        final var result = memberService.grantAdminPermission(testMemberLoginId, true);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
    }
}
