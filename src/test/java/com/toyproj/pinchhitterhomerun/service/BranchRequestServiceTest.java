package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.helper.TestAccountManager;
import com.toyproj.pinchhitterhomerun.helper.TestHelper;
import com.toyproj.pinchhitterhomerun.type.AcceptType;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BranchRequestServiceTest extends TestHelper {
    @Autowired
    BranchRequestService branchRequestService;

    @Autowired
    TestAccountManager testAccountManager;

    static boolean initialized = false;

    @BeforeEach
    public void clean() {
        if (!initialized) {
            testAccountManager.process();
            initialized = true;
        }

        if (testAccountManager.haveBranch()) {
            testAccountManager.removeBranch();
        }

        if (testAccountManager.haveRequest()) {
            testAccountManager.cancelRequest();
        }

        if (TestAccountManager.subTestMember != null) {
            testAccountManager.leaveSubTestAccount();
        }

        testAccountManager.unSetMaster();
    }

    @Test
    public void 지점_알바생_등록_신청() {
        // given
        final var testBranch = getRandomBranch();
        final var memberId = TestAccountManager.testMember.getId();

        // when
        final var result = branchRequestService.requestToBranchMaster(memberId, testBranch.getId());

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var findRequest = branchRequestService.getMemberRequest(memberId);
        assertThat(findRequest.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(findRequest.getResponse().getBranchId()).isEqualTo(testBranch.getId());
        assertThat(findRequest.getResponse().getMemberId()).isEqualTo(memberId);
        assertThat(findRequest.getResponse().getAcceptType()).isNull();
    }

    @Test
    public void 지점에_속한_알바생_다른_지점_등록_신청() {
        // given
        testAccountManager.setBranch(getRandomBranch());
        final var memberBranch = TestAccountManager.testMember.getBranch();
        final var memberId = TestAccountManager.testMember.getId();

        // when
        final var result = branchRequestService.requestToBranchMaster(memberId, memberBranch.getId());

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.REQUEST_ALREADY_HAVE_BRANCH.getMessage());
    }

    @Test
    public void 지점에_연속으로_등록_신청() {
        // given
        final var testBranch = getRandomBranch();
        final var memberId = TestAccountManager.testMember.getId();
        final var request = branchRequestService.requestToBranchMaster(memberId, testBranch.getId());
        assertThat(request.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = branchRequestService.requestToBranchMaster(memberId, testBranch.getId());

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.REQUEST_ALREADY_REQUESTED.getMessage());
    }

    @Test
    public void 다른_지점에_신청후_또_다른_지점_신청() {
        // given
        final var testBranch = getRandomBranch();
        final var testBranch2 = getRandomBranch();
        final var memberId = TestAccountManager.testMember.getId();
        final var request = branchRequestService.requestToBranchMaster(memberId, testBranch.getId());
        assertThat(request.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = branchRequestService.requestToBranchMaster(memberId, testBranch2.getId());

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.REQUEST_ALREADY_REQUESTED.getMessage());
    }

    @Test
    public void 권한없는_사용자가_지점_모든_요청_가져오기() {
        // given
        final var testBranch = getRandomBranch();
        final var memberId = TestAccountManager.testMember.getId();
        final var request = branchRequestService.requestToBranchMaster(memberId, testBranch.getId());
        assertThat(request.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = branchRequestService.getBranchRequest(testBranch.getId(), memberId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.REQUEST_HAVE_NO_PERMISSION.getMessage());
    }

    @Test
    public void 지점_모든_요청_가져오기() {
        // given
        final var testBranch = getRandomBranch();
        final var subTestMember =  testAccountManager.newMember();
        final var memberId = TestAccountManager.testMember.getId();
        testAccountManager.setMaster();
        testAccountManager.removeSubTestAccountBranch();

        final var request = branchRequestService.requestToBranchMaster(subTestMember.getId(), testBranch.getId());
        assertThat(request.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = branchRequestService.getBranchRequest(testBranch.getId(), memberId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isGreaterThan(0);
    }

    @Test
    public void 지점_신청_취소() {
        // given
        final var testBranch = getRandomBranch();
        final var memberId = TestAccountManager.testMember.getId();
        final var request = branchRequestService.requestToBranchMaster(memberId, testBranch.getId());
        assertThat(request.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var findRequest = branchRequestService.getMemberRequest(memberId);
        assertThat(findRequest.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = branchRequestService.cancelRequest(findRequest.getResponse().getId());

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
    }

    @Test
    public void 지점_신청_없이_취소() {
        // given
        final var testRequestId = 0L;

        // when
        final var result = branchRequestService.cancelRequest(testRequestId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.REQUEST_NOT_EXIST.getMessage());
    }

    @Test
    public void 요청_처리_완료된_요청_가져오기() {
        // given
        final var testBranch = getRandomBranch();
        final var memberId = TestAccountManager.testMember.getId();
        final var request = branchRequestService.requestToBranchMaster(memberId, testBranch.getId());
        assertThat(request.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var findRequest = branchRequestService.getMemberRequest(memberId);
        assertThat(findRequest.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var requestDeny = branchRequestService.responseForRequest(findRequest.getResponse().getId(), AcceptType.Deny);
        assertThat(requestDeny.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = branchRequestService.cancelRequest(findRequest.getResponse().getId());

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.REQUEST_NOT_EXIST.getMessage());
    }

    @Test
    public void 요청_수락() {
        // given
        final var testBranch = getRandomBranch();
        final var memberId = TestAccountManager.testMember.getId();
        final var request = branchRequestService.requestToBranchMaster(memberId, testBranch.getId());
        assertThat(request.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var findRequest = branchRequestService.getMemberRequest(memberId);
        assertThat(findRequest.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = branchRequestService.responseForRequest(findRequest.getResponse().getId(), AcceptType.Accept);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
    }

    @Test
    public void 요청_거절() {
        // given
        final var testBranch = getRandomBranch();
        final var memberId = TestAccountManager.testMember.getId();
        final var request = branchRequestService.requestToBranchMaster(memberId, testBranch.getId());
        assertThat(request.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var findRequest = branchRequestService.getMemberRequest(memberId);
        assertThat(findRequest.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = branchRequestService.responseForRequest(findRequest.getResponse().getId(), AcceptType.Deny);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
    }

    @Test
    public void 없는_요청_수락() {
        // given
        final var testRequestId = 0L;

        // when
        final var result = branchRequestService.responseForRequest(testRequestId, AcceptType.Accept);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.REQUEST_NOT_EXIST.getMessage());
    }

    @Test
    public void 없는_요청_거절() {
        // given
        final var testRequestId = 0L;

        // when
        final var result = branchRequestService.responseForRequest(testRequestId, AcceptType.Deny);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.REQUEST_NOT_EXIST.getMessage());
    }

    @Test
    public void 요청_아이디로_요청_찾기() {
        // given
        final var testBranch = getRandomBranch();
        final var memberId = TestAccountManager.testMember.getId();
        final var request = branchRequestService.requestToBranchMaster(memberId, testBranch.getId());
        assertThat(request.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var findRequest = branchRequestService.getMemberRequest(memberId);
        assertThat(findRequest.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = branchRequestService.getBranchRequestById(findRequest.getResponse().getId());

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().getBranchId()).isEqualTo(testBranch.getId());
        assertThat(result.getResponse().getMemberId()).isEqualTo(memberId);
        assertThat(result.getResponse().getAcceptType()).isNull();
    }

    @Test
    public void 없는_요청_아이디로_요청_찾기() {
        // given
        final var testId = 0L;

        // when
        final var result = branchRequestService.getBranchRequestById(testId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.REQUEST_NOT_EXIST.getMessage());
    }
}
