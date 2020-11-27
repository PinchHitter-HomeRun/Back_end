package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.BranchRequest;
import com.toyproj.pinchhitterhomerun.model.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.BranchRequestService;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/request")
public class BranchRequestController {

    private final BranchRequestService branchRequestService;

    // 지점에 알바생 등록 신청
    @PostMapping("/branch/{branchId}")
    public ResponseResult<Boolean> requestToBranchMaster(@RequestParam Long memberId, @PathVariable Long branchId) {
        Map<String, Boolean> result = new HashMap<>();

        BranchRequest request = new BranchRequest(memberId, branchId);

        branchRequestService.requestToBranchMaster(request);

        return new ResponseResult<>(ErrorMessage.SUCCESS, true);
    }
    
    // 지점 신청 취소


    // 요청 수락 or 거절


    // 지점의 모든 요청 가져오기
    @GetMapping("/branch/{branchId}")
    public ResponseResult<List<BranchRequest>> getBranchRequest(@PathVariable Long branchId) {
        List<BranchRequest> result = branchRequestService.getBranchRequest(branchId);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

}
