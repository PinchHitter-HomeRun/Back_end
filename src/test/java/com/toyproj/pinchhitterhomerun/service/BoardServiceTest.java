package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.helper.TestAccountManager;
import com.toyproj.pinchhitterhomerun.helper.TestHelper;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import com.toyproj.pinchhitterhomerun.type.PayType;
import com.toyproj.pinchhitterhomerun.util.TimeManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardServiceTest extends TestHelper {
    @Autowired
    BoardService boardService;

    @Autowired
    BranchService branchService;

    @Autowired
    TestAccountManager testAccountManager;

    static boolean initialized = false;

    @BeforeEach
    public void clean() {
        if (!initialized) {
            testAccountManager.process();
            initialized = true;
        }

        if (!testAccountManager.haveBranch()) {
            testAccountManager.setBranch(getRandomBranch());
        }
    }

    @AfterEach
    public void afterClean() {
        testAccountManager.deleteAllBoard();
        testAccountManager.leaveSubTestAccount();
    }

    @Test
    public void 게시글_작성() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        // when
        final var result = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var after = boardService.getBoardsByMemberId(testMemberId, 0, 10);
        assertThat(after.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(after.getResponse().iterator().next().getTitle()).isEqualTo(title);
    }

    @Test
    public void 게시글_작성_알바_시작_24시간_이전의_글() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now();
        final var endDate = TimeManager.now().plusDays(1);
        final var payType = PayType.Day;
        final var pay = 1000;

        // when
        final var result = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BOARD_HAVE_TO_24_HOURS_BEFORE.getMessage());
    }

    @Test
    public void 게시글_작성_알바_시작_시간이_종료_시간보다_늦을_때() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now();
        final var payType = PayType.Day;
        final var pay = 1000;

        // when
        final var result = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BOARD_INVALID_DATE_TIME.getMessage());
    }

    @Test
    public void 지점에_속하지_않은_사용자가_게시글_작성() {
        // given
        testAccountManager.removeBranch();

        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        // when
        final var result = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BOARD_MEMBER_HAVE_NOT_BRANCH.getMessage());
    }

    @Test
    public void 게시글을_세개_이상_작성한_후_게시글을_더_작성() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;
        final var testTimes = 3;

        for (int i = 1; i <= testTimes; i++) {
            final var write = boardService.writeBoard(
                    testMemberId,
                    title,
                    content,
                    payType,
                    pay,
                    startDate,
                    endDate
            );
            assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        }

        // when
        final var result = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BOARD_CANNOT_WRITE_NO_MORE.getMessage());
    }

    @Test
    public void 존재하지_않는_사용자가_게시글_작성() {
        // given
        final var testMemberId = 0L;
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now();
        final var endDate = TimeManager.now().plusDays(1);
        final var payType = PayType.Day;
        final var pay = 1000;

        // when
        final var result = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
    }

    @Test
    public void 사용자_이름_일부로_게시글_조회() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var testMemberName = TestAccountManager.testMember.getName();
        final var testName = testMemberName.substring(0, 3);
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = boardService.getBoardByContainsName(testName, 0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isGreaterThan(0);
        assertThat(result.getResponse().iterator().next().getAuthor().getName()).isEqualTo(testMemberName);
    }

    @Test
    public void 사용자_이름_일부로_게시글_조회_결과_없음() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var testName = "뭔데";
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = boardService.getBoardByContainsName(testName, 0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isEqualTo(0);
    }

    @Test
    public void 두_글자_미만의_이름으로_게시글_검색() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var testName = "뭔";
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = boardService.getBoardByContainsName(testName, 0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BOARD_SEARCH_KEYWORD_HAVE_TO_OVER_TWO_LETTERS.getMessage());
    }

    @Test
    public void 게시글_작성_후_사용자의_게시글_조회() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = boardService.getBoardsByMemberId(testMemberId, 0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isEqualTo(1);
    }

    @Test
    public void 게시글_작성_없이_사용자의_게시글_조회() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();

        // when
        final var result = boardService.getBoardsByMemberId(testMemberId, 0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isEqualTo(0);
    }

    @Test
    public void 존재하지_않는_사용자의_개시글_조회() {
        // given
        final var testMemberId = 0L;

        // when
        final var result = boardService.getBoardsByMemberId(testMemberId, 0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
    }

    @Test
    public void 브랜드의_게시글_모두_조회() {
        // given
        final var testBranch = TestAccountManager.testMember.getBranch();
        final var branchData = branchService.getBranchById(testBranch.getId());
        assertThat(branchData.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var testBrandId = branchData.getResponse().getBrand().getId();
        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = boardService.getBoardsByBrandId(testBrandId, 0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isEqualTo(1);
    }

    @Test
    public void 게시글_없는_브랜드의_게시글_모두_조회() {
        // given
        final var testBranch = TestAccountManager.testMember.getBranch();
        final var branchData = branchService.getBranchById(testBranch.getId());
        assertThat(branchData.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var testBrandId = branchData.getResponse().getBrand().getId();

        // when
        final var result = boardService.getBoardsByBrandId(testBrandId, 0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isEqualTo(0);
    }

    @Test
    public void 존재하지_않는_브랜드의_게시글_모두_조회() {
        // given
        final var testBrandId = 0L;

        // when
        final var result = boardService.getBoardsByBrandId(testBrandId, 0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BRAND_NOT_EXIST.getMessage());
    }

    @Test
    public void 지점의_게시글_모두_조회() {
        // given
        final var testBranchId = TestAccountManager.testMember.getBranch().getId();
        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = boardService.getBoardsByBranchId(testBranchId, 0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isEqualTo(1);
    }

    @Test
    public void 게시글_없는_지점의_게시글_모두_조회() {
        // given
        final var testBranchId = TestAccountManager.testMember.getBranch().getId();

        // when
        final var result = boardService.getBoardsByBranchId(testBranchId, 0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isEqualTo(0);
    }

    @Test
    public void 존재하지_않는_지점의_게시글_모두_조회() {
        // given
        final var testBranchId = 0L;

        // when
        final var result = boardService.getBoardsByBranchId(testBranchId, 0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BRANCH_NOT_EXIST.getMessage());
    }

    @Test
    public void 게시글_수정() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var expectedTitle = "수정";
        final var expectedContent = "수정된 본문";

        final var expectedStartDate = TimeManager.now().plusDays(1).plusHours(10);
        final var expectedEndDate = TimeManager.now().plusDays(2);

        final var board = boardService.getBoardsByMemberId(testMemberId, 0, 10);
        assertThat(board.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var testBoardId = board.getResponse().iterator().next().getId();

        // when
        final var result = boardService.updateBoard(
                testMemberId,
                testBoardId,
                expectedTitle,
                expectedContent,
                payType,
                pay,
                expectedStartDate,
                expectedEndDate
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var afterBoard = boardService.getBoard(testBoardId);
        assertThat(afterBoard.getResponse().getTitle()).isEqualTo(expectedTitle);
        assertThat(afterBoard.getResponse().getContent()).isEqualTo(expectedContent);
        assertThat(afterBoard.getResponse().getStarDate()).isEqualTo(expectedStartDate);
        assertThat(afterBoard.getResponse().getEndDate()).isEqualTo(expectedEndDate);
    }

    @Test
    public void 없는_게시글_수정() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var testBoardId = 0L;
        final var updateTitle = "수정";
        final var updateContent = "수정된 본문";
        final var updateStartDate = TimeManager.now().plusDays(1).plusHours(10);
        final var updateEndDate = TimeManager.now().plusDays(2);
        final var updatePayType = PayType.Hour;
        final var updatePay = 1;

        // when
        final var result = boardService.updateBoard(
                testMemberId,
                testBoardId,
                updateTitle,
                updateContent,
                updatePayType,
                updatePay,
                updateStartDate,
                updateEndDate
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BOARD_NOT_EXIST.getMessage());
    }

    @Test
    public void 사용자가_작성하지_않은_글_수정() {
        // given
        final var originMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                originMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var board = boardService.getBoardsByMemberId(originMemberId, 0, 10);
        assertThat(board.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var testBoardId = board.getResponse().iterator().next().getId();

        final var newMember = testAccountManager.newMember();

        final var testMemberId = newMember.getId();

        final var updateTitle = "수정";
        final var updateContent = "수정된 본문";
        final var updateStartDate = TimeManager.now().plusDays(1).plusHours(10);
        final var updateEndDate = TimeManager.now().plusDays(2);
        final var updatePayType = PayType.Hour;
        final var updatePay = 1;

        // when
        final var result = boardService.updateBoard(
                testMemberId,
                testBoardId,
                updateTitle,
                updateContent,
                updatePayType,
                updatePay,
                updateStartDate,
                updateEndDate
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BOARD_CAN_NOT_UPDATE_NOT_MINE.getMessage());
    }

    @Test
    public void 게시글_삭제() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var board = boardService.getBoardsByMemberId(testMemberId, 0, 10);
        assertThat(board.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var testBoardId = board.getResponse().iterator().next().getId();

        // when
        final var result = boardService.deleteBoard(testMemberId, testBoardId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var after = boardService.getBoard(testBoardId);
        assertThat(after.getResult()).isEqualTo(ErrorMessage.BOARD_NOT_EXIST.getMessage());
    }

    @Test
    public void 없는_게시글_삭제() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var testId = 0L;

        // when
        final var result = boardService.deleteBoard(testMemberId, testId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BOARD_NOT_EXIST.getMessage());
    }

    @Test
    public void 사용자가_작성하지_않은_글_삭제() {
        // given
        final var originMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                originMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var board = boardService.getBoardsByMemberId(originMemberId, 0, 10);
        assertThat(board.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var testBoardId = board.getResponse().iterator().next().getId();

        final var newMember = testAccountManager.newMember();

        final var testMemberId = newMember.getId();

        // when
        final var result = boardService.deleteBoard(testMemberId, testBoardId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BOARD_CAN_NOT_UPDATE_NOT_MINE.getMessage());
    }

    @Test
    public void 게시글_조회() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var board = boardService.getBoardsByMemberId(testMemberId, 0, 10);
        assertThat(board.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var testBoardId = board.getResponse().iterator().next().getId();

        // when
        final var result = boardService.getBoard(testBoardId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().getTitle()).isEqualTo(title);
        assertThat(result.getResponse().getContent()).isEqualTo(content);
    }

    @Test
    public void 없는_게시글_조회() {
        // given
        final var testId = 0L;

        // when
        final var result = boardService.getBoard(testId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BOARD_NOT_EXIST.getMessage());
    }

    @Test
    public void 모든_게시글_조회() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = boardService.getAllBoards(0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isGreaterThan(0);
    }

    @Test
    public void 어떤_게시글도_없을_때_모든_게시글_조회() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();

        // when
        final var result = boardService.getAllBoards(0, 10);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isEqualTo(0);
    }

    @Test
    public void 지점_시_구_도로_이름으로_게시글_조회() {
        // given
        final var testBranch = getRandomBranch();
        final var testBrandId = testBranch.getBrand().getId();

        testAccountManager.setBranch(testBranch);

        final var testMemberId = TestAccountManager.testMember.getId();
        final var title = "제목";
        final var content = "제목";
        final var startDate = TimeManager.now().plusDays(1).plusHours(1);
        final var endDate = TimeManager.now().plusDays(2);
        final var payType = PayType.Day;
        final var pay = 1000;

        final var write = boardService.writeBoard(
                testMemberId,
                title,
                content,
                payType,
                pay,
                startDate,
                endDate
        );
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var testCity = testBranch.getAddress().getCity();
        final var testGu = testBranch.getAddress().getGu();
        final var testStreet = testBranch.getAddress().getSub();

        // when
        final var result = boardService.getBoardsByAddress(
                testBrandId,
                testCity,
                testGu,
                testStreet,
                0,
                10
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isGreaterThan(0);
    }

    @Test
    public void 지점_시_구_도로_이름으로_게시글_조회_조회_결과_없음() {
        // given
        final var testBranch = getRandomBranch();
        final var testBrandId = testBranch.getBrand().getId();

        testAccountManager.setBranch(testBranch);

        final var testCity = testBranch.getAddress().getCity();
        final var testGu = testBranch.getAddress().getGu();
        final var testStreet = testBranch.getAddress().getSub();

        final var result = boardService.getBoardsByAddress(
                testBrandId,
                testCity,
                testGu,
                testStreet,
                1,
                10
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isEqualTo(0);
    }
}