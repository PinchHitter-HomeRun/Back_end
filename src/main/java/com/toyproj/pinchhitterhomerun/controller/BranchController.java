package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.Branch;
import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.service.BranchService;
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
    public List<Branch> searchBranch(@PathVariable Long brandId, String city, String sub, String text) {
        return branchService.searchBranch(brandId, city, sub, text);
    }

    // 아이디로 지점 가져오기
    @GetMapping("/id/{id}")
    public Branch getBranchById(@PathVariable Long id) {
        return branchService.getBranchById(id);
    }

    @GetMapping("/{brandId}/{name}")
    public Branch getBranchByName(@PathVariable Long brandId, @PathVariable String name) {
        return branchService.getBranchByBranchIdAndName(brandId, name);
    }

    // 지점에 속한 사용자들 가져오기
    @GetMapping("/branch/{branchId}")
    public Map<String, List<Member>> getBranchMembers(@PathVariable Long branchId) {
        Map<String, List<Member>> result = new HashMap<>();

        result.put("result", branchService.getBranchMembers(branchId));

        return result;
    }
}
