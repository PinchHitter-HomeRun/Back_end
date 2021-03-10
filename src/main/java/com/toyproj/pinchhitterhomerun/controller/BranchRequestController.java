package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.BranchRequest;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.request.BranchJoinReq;
import com.toyproj.pinchhitterhomerun.request.BranchJoinResponseReq;
import com.toyproj.pinchhitterhomerun.service.BranchRequestService;
import com.toyproj.pinchhitterhomerun.type.AcceptType;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/request")
public class BranchRequestController {

    @Autowired
    BranchRequestService branchRequestService;

    @ApiOperation("알바생 지점에 등록 요청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "지점 가입 요청\n" +
                    "memberId : 요청하는 사용자의 ID\n" +
                    "branchId : 가입 요청할 지점 ID", required = true, dataType = "BranchJoinReq")
    })
    @ResponseBody
    @PutMapping
    public ResponseResult<Void> requestToBranchMaster(@RequestBody BranchJoinReq request) {
        final var response = branchRequestService
                .requestToBranchMaster(request.getMemberId(), request.getBranchId());

        return new ResponseResult<>(response);
    }

    @ApiOperation("지점에 알바생 등록 요청 취소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestId", value = "요청 ID", required = true, dataType = "long", example = "0")
    })
    @ResponseBody
    @DeleteMapping("/{requestId}")
    public ResponseResult<Void> cancelRequest(@PathVariable("requestId") Long requestId) {
        final var response = branchRequestService.cancelRequest(requestId);

        return new ResponseResult<>(response);
    }

    @ApiOperation("지점 알바생 층록 요청에 대한 수락 or 거절")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestId", value = "요청 ID", required = true, dataType = "long", example = "0"),
            @ApiImplicitParam(name = "request", value = "지점 가입 요청에 대한 응답 request\n" +
                    "response : 요청에대한 응답 (1 - 승인, 2 - 거절)", required = true, dataType = "BranchJoinResponseReq")
    })
    @ResponseBody
    @PutMapping("/{requestId}")
    public ResponseResult<Void> responseToRequest(@PathVariable("requestId") Long requestId,
                                                  @RequestBody BranchJoinResponseReq request) {
        final var response = branchRequestService
                .responseForRequest(requestId, AcceptType.fromInt(request.getResponse()));

        return new ResponseResult<>(response);
    }

    @ApiOperation("자신의 지점의 모든 요청 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "조회할 지점 ID", required = true, dataType = "long", example = "0"),
            @ApiImplicitParam(name = "memberId", value = "조회를 하는 사용자 ID", required = true, dataType = "long", example = "0")
    })
    @ResponseBody
    @GetMapping("/branch/{branchId}")
    public ResponseResult<Collection<BranchRequest>> getBranchRequest(@PathVariable("branchId") Long branchId,
                                                                      @RequestParam("memberId") Long memberId) {
        final var response = branchRequestService.getBranchRequest(branchId, memberId);

        return new ResponseResult<>(response);
    }
}
