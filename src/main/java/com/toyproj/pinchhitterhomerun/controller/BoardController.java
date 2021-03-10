package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.bean.MemberBoardBean;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.request.BoardWriteReq;
import com.toyproj.pinchhitterhomerun.response.BoardContentRes;
import com.toyproj.pinchhitterhomerun.response.BoardTitleRes;
import com.toyproj.pinchhitterhomerun.service.BoardService;
import com.toyproj.pinchhitterhomerun.type.PayType;
import com.toyproj.pinchhitterhomerun.util.BeanToProtocol;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    BoardService boardService;

    @ApiOperation("게시글 작성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "게시글 작성 request\n" +
                    "memberId : 작성자 ID\n" +
                    "title : 제목\n" +
                    "content : 내용\n" +
                    "payType : 페이 유형 (1 - 시급, 2 - 일급)" +
                    "pay : 페이 금액\n" +
                    "startDate : 알바 시작 일시\n" +
                    "endData : 알바 종료 일시", required = true, dataType = "BoardWriteReq")
    })
    @ResponseBody
    @PutMapping
    public ResponseResult<Void> writeBoard(@RequestBody BoardWriteReq request) {
        final var result = boardService.writeBoard(
                request.getMemberId(),
                request.getTitle(),
                request.getContent(),
                PayType.fromInt(request.getPayType()),
                request.getPay(),
                request.getStartDate(),
                request.getEndDate()
        );

        return new ResponseResult<>(result);
    }

    @ApiOperation("모든 게시글 리스트 조회")
    @ResponseBody
    @GetMapping
    public ResponseResult<BoardTitleRes> getAllBoard() {
        final var response = new BoardTitleRes();
        final var findBoards = boardService.getAllBoards();

        if (!findBoards.isSuccess()) {
            return new ResponseResult<>(response).setResult(findBoards.getResult()).build();
        }

        response.setBoardTitleBeans(findBoards.getResponse());

        return new ResponseResult<>(response);
    }

    @ApiOperation("게시글 내용 보기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "내용을 조회할 게시글 ID", required = true, dataType = "long", example = "0")
    })
    @ResponseBody
    @GetMapping("/{boardId}")
    public ResponseResult<BoardContentRes> getBoardContent(@PathVariable("boardId") Long boardId) {
        final var response = new BoardContentRes();
        final var boardContent = boardService.getBoard(boardId);

        if (!boardContent.isSuccess()) {
            return new ResponseResult<>(response).setResult(boardContent.getResult()).build();
        }

        BeanToProtocol.copyPropertyBoardContentRes(boardContent.getResponse(), response);

        return new ResponseResult<>(response);
    }

    @ApiOperation("해당 브랜드의 게시글 리스트 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "브랜드 이름\n" +
                    "1 - 탐앤탐스\n" +
                    "2 - 스타벅스\n" +
                    "3 - 투썸플레이스\n" +
                    "4 - 엔제리너스\n" +
                    "5 - 할리스\n" +
                    "6 - 맥도날드\n" +
                    "7 - 롯데리아\n" +
                    "8 - 버거킹\n" +
                    "9 - CU\n" +
                    "10 - GS25\n" +
                    "11 - 미니스톱\n" +
                    "12 - 이마트24\n" +
                    "13 - 롯데시네마\n" +
                    "14 - CGV\n" +
                    "15 - 메가박스\n" +
                    "16 - 올리브영\n" +
                    "17 - 아리따움\n" +
                    "18 - 롭스\n" +
                    "19 - 토니모리", required = true, dataType = "String")
    })
    @ResponseBody
    @GetMapping("/brand")
    public ResponseResult<BoardTitleRes> getBrandBoards(@RequestParam("brandId") Long brandId) {
        final var response = new BoardTitleRes();
        final var findBoards = boardService.getBoardsByBrandId(brandId);

        if (!findBoards.isSuccess()) {
            return new ResponseResult<>(response).setResult(findBoards.getResult());
        }

        response.setBoardTitleBeans(findBoards.getResponse());

        return new ResponseResult<>(response);
    }

    @ApiOperation("해당 지점의 게시글 리스트 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "지점 ID", required = true, dataType = "long", example = "0")
    })
    @ResponseBody
    @GetMapping("/branch")
    public ResponseResult<BoardTitleRes> getBranchBoards(@RequestParam("branchId") Long branchId) {
        final var response = new BoardTitleRes();
        final var findBoards = boardService.getBoardsByBranchId(branchId);

        if (!findBoards.isSuccess()) {
            return new ResponseResult<>(response).setResult(findBoards.getResult());
        }

        response.setBoardTitleBeans(findBoards.getResponse());

        return new ResponseResult<>(response);
    }

    @ApiOperation("특정 사용자가 작성한 게시글 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "사용자 ID", required = true, dataType = "long", example = "0")
    })
    @ResponseBody
    @GetMapping("/member")
    public ResponseResult<BoardTitleRes> getMemberBoards(@RequestParam("memberId") Long memberId) {
        final var response = new BoardTitleRes();
        final var findBoards = boardService.getBoardsByBranchId(memberId);

        if (!findBoards.isSuccess()) {
            return new ResponseResult<>(response).setResult(findBoards.getResult());
        }

        response.setBoardTitleBeans(findBoards.getResponse());

        return new ResponseResult<>(response);
    }

    @ApiOperation("게시글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "수정할 게시글 ID", required = true, dataType = "long", example = "0"),
            @ApiImplicitParam(name = "request", value = "게시글 수정 request\n" +
                    "memberId : 작성자 ID\n" +
                    "title : 제목\n" +
                    "content : 내용\n" +
                    "payType : 페이 유형 (1 - 시급, 2 - 일급)" +
                    "pay : 페이 금액\n" +
                    "startDate : 알바 시작 일시\n" +
                    "endData : 알바 종료 일시", required = true, dataType = "BoardWriteReq")
    })
    @ResponseBody
    @PostMapping("/{boardId}")
    public ResponseResult<Void> updateBoard(@PathVariable("boardId") Long boardId,
                                            @RequestBody BoardWriteReq request) {
        final var result = boardService.updateBoard(
                request.getMemberId(),
                boardId,
                request.getTitle(),
                request.getContent(),
                PayType.fromInt(request.getPayType()),
                request.getPay(),
                request.getStartDate(),
                request.getEndDate()
        );

        return new ResponseResult<>(result);
    }

    @ApiOperation("게시글 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "삭제할 게시글 ID", required = true, dataType = "long", example = "0"),
            @ApiImplicitParam(name = "memberId", value = "삭제 요청을하는 사용자 ID", required = true, dataType = "long", example = "0")
    })
    @ResponseBody
    @DeleteMapping("/{boardId}")
    public ResponseResult<Void> deleteBoard(@PathVariable("boardId") Long boardId,
                                            @RequestParam("memberId") Long memberId) {
        final var result = boardService.deleteBoard(memberId, boardId);

        return new ResponseResult<>(result);
    }
}
