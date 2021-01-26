package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BranchException;
import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.repository.BranchRepository;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final BrandService brandService;
    private final MemberRepository memberRepository;

    // 아이디로 지점 가져오기
    public Branch getBranchById(Long id) {
        Branch branch = branchRepository.findById(id);

        if (branch == null) {
            throw new BranchException(ErrorMessage.BRANCH_NOT_EXIST);
        }

        return branch;
    }

    // 지점명과 브랜드로 지점 가져오기
    public Branch getBranchByBranchIdAndName(Long brandId, String brandName) {
        Branch branch;

        try {
            branch = branchRepository.findByBrandAndName(brandId, brandName);
        } catch (Exception e) {
            throw new BranchException(ErrorMessage.BRANCH_NOT_EXIST);
        }

        return branch;
    }

    public Branch getBranchByName(String name) {
        Branch branch;

        try {
            branch = branchRepository.findByName(name);
        } catch (Exception e) {
            throw new BranchException(ErrorMessage.BRANCH_NOT_EXIST);
        }

        return branch;
    }

    // 시, 구, 지점명으로 지점 검색
    public List<Branch> searchBranch(Long brandId, String city, String gu, String branchName) {
        List<Branch> branches;

        try {
            branches = branchRepository.searchByKeywordWithBrandId(brandId, city, gu, branchName);
        } catch (Exception e) {
            throw new BranchException(ErrorMessage.BRANCH_NOT_FOUND);
        }

        return branches;
    }

    // 시, 구로 지점 검색
    public List<Branch> searchBranch(Long brandId, String city, String gu) {
        List<Branch> branches;

        try {
            branches = branchRepository.searchByKeywordWithBrandId(brandId, city, gu, null);
        } catch (Exception e) {
            throw new BranchException(ErrorMessage.BRANCH_NOT_FOUND);
        }

        return branches;
    }

    // 브랜드에 속한 모든 지점 가져오기
    public List<Branch> getBranchByBrandId(Long brandId) {
        List<Branch> branches;

        branches = branchRepository.findByBrandId(brandId);

        if (branches.size() == 0) {
            brandService.getBrandById(brandId);
        }

        return branches;
    }

    // 주소로 지점 가져오기
    public Branch getBranchByAddress(String address) {
        Branch branch;

        try {
            branch = branchRepository.findByAddress(address);
        } catch (Exception e) {
            throw new BranchException(ErrorMessage.BRANCH_NOT_FOUND);
        }

        return branch;
    }

    // 지점에 속한 모든 인원 찾기
    public List<Member> getBranchMembers(Long branchId) {
        List<Member> members;

        members = memberRepository.findByBranchId(branchId);

        if (members.size() == 0) {
            throw new BranchException(ErrorMessage.BRANCH_NOT_EXIST);
        }

        return members;
    }
}
