package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.model.Branch;
import com.toyproj.pinchhitterhomerun.repository.BranchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    // 아이디로 지점 가져오기
    public Branch getBranchById(Long id) {
        return branchRepository.findById(id);
    }

    // 시, 서브 주소와 검색어로 지점 검색
    public List<Branch> searchBranch(String city, String sub, String text) {
        return branchRepository.searchByKeyword(city, sub, text);
    }

    // 사용자의 지점 가져오기
    public Branch getMemberBranch(Long memberId) {
        return branchRepository.findByMemberId(memberId);
    }
}
