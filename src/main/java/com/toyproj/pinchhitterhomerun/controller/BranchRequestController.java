package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.BranchRequest;
import com.toyproj.pinchhitterhomerun.service.BranchRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, Boolean> requestToBranchMaster(@RequestParam Long memberId, @PathVariable Long branchId) {
        Map<String, Boolean> result = new HashMap<>();

        branchRequestService.requestToBranchMaster(memberId, branchId);

        result.put("result", true);

        return result;
    }
    
    // 지점 신청 취소


    // 요청 수락 or 거절


    // 지점의 모든 요청 가져오기
    @GetMapping("/branch/{branchId}")
    public Map<String, List<BranchRequest>> getBranchReqeust(@PathVariable Long branchId) {
        Map<String, List<BranchRequest>> result = new HashMap<>();

        List<BranchRequest> requests = branchRequestService.getBranchRequest(branchId);

        result.put("result", requests);

        return result;
    }

}
