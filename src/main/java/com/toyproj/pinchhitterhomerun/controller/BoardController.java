package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.bean.MemberBoardBean;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.request.BoardWriteReq;
import com.toyproj.pinchhitterhomerun.response.BoardContentRes;
import com.toyproj.pinchhitterhomerun.response.BoardTitleRes;
import com.toyproj.pinchhitterhomerun.service.BoardService;
import com.toyproj.pinchhitterhomerun.type.PayType;
import com.toyproj.pinchhitterhomerun.util.BeanToProtocol;
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
    @ResponseBody
    @DeleteMapping("/{boardId}")
    public ResponseResult<Void> deleteBoard(@PathVariable("boardId") Long boardId,
                                            @RequestParam("memberId") Long memberId) {
        final var result = boardService.deleteBoard(memberId, boardId);

        return new ResponseResult<>(result);
    }
}
