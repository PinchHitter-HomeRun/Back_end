package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.Branch;
import com.toyproj.pinchhitterhomerun.service.BranchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    // 지점 검색
    @PostMapping("/")
    public List<Branch> searchBranch(String city, String sub, String text) {
        return branchService.searchBranch(city, sub, text);
    }

    // 멤버가 속한 지점 가져오기
    @GetMapping("/member/{memberId}")
    public Branch getMemberBranch(@PathVariable Long memberId) {
        return branchService.getMemberBranch(memberId);
    }

    // 아이디로 지점 가져오기
    @GetMapping("/{id}")
    public Branch getBranchById(@PathVariable Long id) {
        return branchService.getBranchById(id);
    }
}
