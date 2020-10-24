package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BranchException;
import com.toyproj.pinchhitterhomerun.exception.BrandException;
import com.toyproj.pinchhitterhomerun.model.Branch;
import com.toyproj.pinchhitterhomerun.repository.BranchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    // 아이디로 지점 가져오기
    public Branch getBranchById(Long id) {
        Branch branch = branchRepository.findById(id);

        if (branch == null) {
            throw new BranchException("존재하지 않는 지점입니다.");
        }

        return branch;
    }

    // 지점명과 브랜드로 지점 가져오기
    public Branch getBranchByBranchIdAndName(Long brandId, String name) {
        Branch branch;

        try {
            branch = branchRepository.findByBrandAndName(brandId, name);
        } catch (Exception e) {
            throw new BranchException("존재하지 않는 지점입니다.");
        }

        return branch;
    }

    // 주소로 지점 가져오기
    public Branch getBranchByAddress(String address) {
        Branch branch;

        try {
            branch = branchRepository.findByAddress(address);
        } catch (Exception e) {
            throw new BranchException("존재하지 않는 지점입니다.");
        }

        return branch;
    }

    // 시, 서브 주소와 검색어로 지점 검색
    public List<Branch> searchBranch(Long brandId, String city, String sub, String text) {
        List<Branch> branches;

        try {
            branches = branchRepository.searchByKeywordWithBrandId(brandId, city, sub, text);
        } catch (Exception e) {
            throw new BranchException("조회된 지점이 없습니다.");
        }

        return branches;
    }

    // 브랜드에 속한 모든 지점 가져오기
    public List<Branch> getBranchByBrandId(Long brandId) {
        List<Branch> branches;

        branches = branchRepository.findByBrandId(brandId);

        if (branches.size() == 0) {
            throw new BranchException("존재하지 않는 브랜드 입니다.");
        }

        return branches;
    }

    // 사용자의 지점 가져오기
    public Branch getMemberBranch(Long memberId) {
        Branch branch;

        try {
            branch = branchRepository.findByMemberId(memberId);
        } catch (Exception e) {
            throw new BranchException("사용자가 속한 지점이 없습니다.");
        }

        return branch;
    }
}
