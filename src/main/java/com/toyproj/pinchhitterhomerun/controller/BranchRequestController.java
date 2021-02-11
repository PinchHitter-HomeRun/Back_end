package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.BranchRequest;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.BranchRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/request")
public class BranchRequestController {

    private final BranchRequestService branchRequestService;

    /**
     * 알바생 등록 신청
     */
    @PutMapping
    public ResponseResult<Void> requestToBranchMaster(@RequestParam Long memberId, @RequestParam Long branchId) {
        final var response = branchRequestService.requestToBranchMaster(memberId, branchId);

        return new ResponseResult<>(response);
    }

    /**
     * 지점에 요청한 요청 취소
     */
    @DeleteMapping("/{id}/cancel")
    public ResponseResult<Void> cancelRequest(@PathVariable("id") Long requestId) {
        final var response = branchRequestService.cancelRequest(requestId);

        return new ResponseResult<>(response);
    }

    // 요청 수락 or 거절


    // 지점의 모든 요청 가져오기
    @GetMapping("/branch/{branchId}")
    public ResponseResult<Collection<BranchRequest>> getBranchRequest(@PathVariable("branchId") Long branchId) {
        final var response = branchRequestService.getBranchRequest(branchId);

        return new ResponseResult<>(response);
    }

}
