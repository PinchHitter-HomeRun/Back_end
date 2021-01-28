package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.exception.BranchException;
import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.repository.BranchRepository;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class BranchService {

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    BrandService brandService;

    @Autowired
    MemberRepository memberRepository;

    /**
     * ID로 지점 가져오기
     */
    public ServiceResult<Branch> getBranchById(Long id) {
        final var branch = branchRepository.findById(id);

        if (branch == null) {
            return new ServiceResult<>(ErrorMessage.BRANCH_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, branch);
    }

    /**
     * 브랜드ID와 지점명으로 지점 가져오기
     */
    public ServiceResult<Branch> getBranchByBrandIdAndName(Long brandId, String brandName) {
        final var branch = branchRepository.findByBrandAndName(brandId, brandName);

        if (branch == null) {
            return new ServiceResult<>(ErrorMessage.BRANCH_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, branch);
    }

    /**
     * 지점명으로 지점 가져오기
     */
    public ServiceResult<Branch> getBranchByName(String branchName) {
        final var branch = branchRepository.findByName(branchName);

        if (branch == null) {
            return new ServiceResult<>(ErrorMessage.BRANCH_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, branch);
    }

    /**
     * 시, 구, 지점명으로 지점 검색
     */
    public ServiceResult<Collection<Branch>> searchBranch(Long brandId, String city, String gu, String branchName) {
        final var branches = branchRepository.searchByKeywordWithBrandId(brandId, city, gu, branchName);

        if (branches.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.BRANCH_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, branches);
    }

    /**
     * 시, 구로 지점 검색
     */
    public ServiceResult<Collection<Branch>> searchBranch(Long brandId, String city, String gu) {
        final var branches = branchRepository.searchByKeywordWithBrandId(brandId, city, gu, null);

        if (branches.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.BRANCH_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, branches);
    }

    /**
     * 브랜드에 속한 모든 지점 가져오기
     */
    public ServiceResult<Collection<Branch>> getBranchByBrandId(Long brandId) {
        final var branches = branchRepository.findByBrandId(brandId);

        if (branches.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.BRANCH_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, branches);
    }

    /**
     * 주소로 지점 가져오기
     */
    public ServiceResult<Branch> getBranchByAddress(String address) {
        final var branch = branchRepository.findByAddress(address);

        if (branch == null) {
            return new ServiceResult<>(ErrorMessage.BRANCH_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, branch);
    }

    /**
     * 지점에 속한 모든 인원 찾기
     */
    public ServiceResult<Collection<Member>> getBranchMembers(Long branchId) {
        final var members = memberRepository.findByBranchId(branchId);

        if (members.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.BRANCH_EMPTY_EMPLOYEE);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, members);
    }
}
