package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.service.BranchService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/branches")
public class BranchController {

    private final BranchService branchService;

    // 지점 검색
    @ApiOperation("지점 검색 (city - 시, gu - 구, branchName - 지점 명)")
    @GetMapping("/{brandId}")
    public List<Branch> searchBranch(@PathVariable("brandId") Long brandId,
                                                     @RequestParam("city") String city,
                                                     @RequestParam("gu") String gu,
                                                     @RequestParam("branchName") String branchName) {

        List<Branch> result = branchService.searchBranch(brandId, city, gu, branchName);

        return result;
    }

    // 아이디로 지점 가져오기
    @ApiOperation("컬럼 id 값으로 지점 검색")
    @GetMapping("/id/{id}")
    public Branch getBranchById(@PathVariable("id") Long id) {

        Branch result = branchService.getBranchById(id);

        return result;
    }

    @ApiOperation("지점이름으로 지점 검색")
    @GetMapping("/{brandId}/{brandName}")
    public Branch getBranchByName(@PathVariable("brandId") Long brandId,
                                                  @PathVariable("brandName") String brandName) {

        Branch result = branchService.getBranchByBranchIdAndName(brandId, brandName);

        return result;
    }

    // 지점에 속한 사용자들 가져오기
    @ApiOperation("지점에 속한 사용자들 검색")
    @GetMapping("/branch/{branchId}")
    public List<Member> getBranchMembers(@PathVariable("branchId") Long branchId) {

        List<Member> result = branchService.getBranchMembers(branchId);

        return result;
    }
}
