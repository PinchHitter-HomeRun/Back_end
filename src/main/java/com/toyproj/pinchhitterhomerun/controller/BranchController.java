package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.Branch;
import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.model.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.BranchService;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/branches")
public class BranchController {

    private final BranchService branchService;

    // 지점 검색
    @PostMapping("/{brandId}")
    public ResponseResult<List<Branch>> searchBranch(@PathVariable Long brandId, String city, String sub, String text) {

        List<Branch> result = branchService.searchBranch(brandId, city, sub, text);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 아이디로 지점 가져오기
    @GetMapping("/id/{id}")
    public ResponseResult<Branch> getBranchById(@PathVariable Long id) {

        Branch result = branchService.getBranchById(id);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    @GetMapping("/{brandId}/{name}")
    public ResponseResult<Branch> getBranchByName(@PathVariable Long brandId, @PathVariable String name) {

        Branch result = branchService.getBranchByBranchIdAndName(brandId, name);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 지점에 속한 사용자들 가져오기
    @GetMapping("/branch/{branchId}")
    public ResponseResult<List<Member>> getBranchMembers(@PathVariable Long branchId) {

        List<Member> result = branchService.getBranchMembers(branchId);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }
}
