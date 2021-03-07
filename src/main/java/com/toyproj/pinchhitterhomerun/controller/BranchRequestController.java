package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.BranchRequest;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.request.BranchJoinReq;
import com.toyproj.pinchhitterhomerun.request.BranchJoinResponseReq;
import com.toyproj.pinchhitterhomerun.service.BranchRequestService;
import com.toyproj.pinchhitterhomerun.type.AcceptType;
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
    @ResponseBody
    @PutMapping
    public ResponseResult<Void> requestToBranchMaster(@RequestBody BranchJoinReq request) {
        final var response = branchRequestService
                .requestToBranchMaster(request.getMemberId(), request.getBranchId());

        return new ResponseResult<>(response);
    }

    @ApiOperation("지점에 알바생 등록 요청 취소")
    @ResponseBody
    @DeleteMapping("/{requestId}")
    public ResponseResult<Void> cancelRequest(@PathVariable("requestId") Long requestId) {
        final var response = branchRequestService.cancelRequest(requestId);

        return new ResponseResult<>(response);
    }

    @ApiOperation("지점 알바생 층록 요청에 대한 수락 or 거절")
    @ResponseBody
    @PutMapping("/{requestId}")
    public ResponseResult<Void> responseToRequest(@PathVariable("requestId") Long requestId,
                                                  @RequestBody BranchJoinResponseReq request) {
        final var response = branchRequestService
                .responseForRequest(requestId, AcceptType.fromInt(request.getResponse()));

        return new ResponseResult<>(response);
    }

    @ApiOperation("자신의 지점의 모든 요청 조회")
    @ResponseBody
    @GetMapping("/branch/{branchId}")
    public ResponseResult<Collection<BranchRequest>> getBranchRequest(@PathVariable("branchId") Long branchId) {
        final var response = branchRequestService.getBranchRequest(branchId);

        return new ResponseResult<>(response);
    }

}
