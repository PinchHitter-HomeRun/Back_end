package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.service.BranchService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {

    @Autowired
    BranchService branchService;

    @ApiOperation("지점 검색 (city - 시, gu - 구, branchName - 지점 명)")
    @ResponseBody
    @GetMapping("/search")
    public ResponseResult<Collection<Branch>> searchBranch(@PathVariable("brandId") Long brandId,
                                                           @RequestParam("city") String city,
                                                           @RequestParam("gu") String gu,
                                                           @RequestParam("branchName") String branchName) {
        final var result = branchService.searchBranch(brandId, city, gu, branchName);

        return new ResponseResult<>(result);
    }

    @ApiOperation("지점이름으로 지점 검색")
    @ResponseBody
    @GetMapping
    public ResponseResult<Branch> getBranchByName(@RequestParam("branchName") String branchName) {
        final var result = branchService.getBranchByName(branchName);

        return new ResponseResult<>(result);
    }

    // 지점에 속한 사용자들 가져오기
    @ApiOperation("지점에 속한 사용자들 검색")
    @ResponseBody
    @GetMapping("/{branchId}/members")
    public ResponseResult<Collection<Member>> getBranchMembers(@PathVariable("branchId") Long branchId) {
        final var result = branchService.getBranchMembers(branchId);

        return new ResponseResult<>(result);
    }
}
