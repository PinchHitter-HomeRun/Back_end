package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.BranchRequest;
import com.toyproj.pinchhitterhomerun.service.BranchRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/request")
public class BranchRequestController {

    private final BranchRequestService branchRequestService;

    // 지점에 알바생 등록 신청
    @PostMapping("/branch/{branchId}")
    public Boolean requestToBranchMaster(@RequestParam Long memberId, @PathVariable Long branchId) {
        BranchRequest request = new BranchRequest(memberId, branchId);

        branchRequestService.requestToBranchMaster(request);

        return true;
    }
    
    // 지점 신청 취소
//    @DeleteMapping("/branch/cancel")
//    public ResponseResult<Boolean> cancelRequest() {
//
//        return new ResponseResult<>(ErrorMessage.SUCCESS);
//    }

    // 요청 수락 or 거절


    // 지점의 모든 요청 가져오기
    @GetMapping("/branch/{branchId}")
    public List<BranchRequest> getBranchRequest(@PathVariable Long branchId) {
        List<BranchRequest> result = branchRequestService.getBranchRequest(branchId);

        return result;
    }

}
