package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.Board;
import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.helper.TestAccountManager;
import com.toyproj.pinchhitterhomerun.helper.TestHelper;
import com.toyproj.pinchhitterhomerun.requestbean.BoardReq;
import com.toyproj.pinchhitterhomerun.type.PayType;
import com.toyproj.pinchhitterhomerun.util.TimeManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest extends TestHelper {
    @Autowired
    BoardService boardService;

    @Autowired
    TestAccountManager testAccountManager;

    private class BoardSet {
        public ServiceResult<Collection<Board>> getBoard(Long memberId) {
            return boardService.getBoardByMemberId(memberId);
        }
    }

    BoardSet boardSet = new BoardSet();
    static boolean initialized = false;

    @BeforeEach
    public void clean() {
        if (!initialized) {
            testAccountManager.process();
            initialized = true;
        }

        if (!testAccountManager.haveBranch()) {
            testAccountManager.setBranch();
        }
    }

    @Test
    public void 게시글_작성() {
        // given
        final var testMemberId = TestAccountManager.testMember.getId();
        final var boardReq = new BoardReq();
        
        boardReq.setTitle("제목");
        boardReq.setContent("내용");
        boardReq.setStartDate(TimeManager.now());
        boardReq.setEndDate(TimeManager.now().plusDays(1));
        boardReq.setPayType(PayType.Day);
        boardReq.setPay(1000);


        // when
        final var result = boardService.writeBoard(testMemberId, boardReq);

        // then
    }
}