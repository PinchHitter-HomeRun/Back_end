package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.*;
import com.toyproj.pinchhitterhomerun.exception.BranchRequestException;
import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.exception.RoleException;
import com.toyproj.pinchhitterhomerun.repository.BranchRepository;
import com.toyproj.pinchhitterhomerun.repository.BranchRequestRepository;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import com.toyproj.pinchhitterhomerun.repository.RoleRepository;
import com.toyproj.pinchhitterhomerun.type.AcceptType;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import com.toyproj.pinchhitterhomerun.util.TimeManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class BranchRequestService {

    @Autowired
    BranchRequestRepository branchRequestRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RoleRepository roleRepository;

    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    /**
     * 지점에 알바생 등록 신청
     */
    public ServiceResult<Void> requestToBranchMaster(Long memberId, Long branchId) {

        final var findMember = memberRepository.findById(memberId);

        if (findMember.getBranch() != null) {
            return new ServiceResult<>(ErrorMessage.REQUEST_ALREADY_HAVE_BRANCH);
        }

        // 중복 요청이 있는지 확인
        final var checkDuplicate = branchRequestRepository.findByMemberId(memberId);

        if (checkDuplicate != null) {
            return new ServiceResult<>(ErrorMessage.REQUEST_ALREADY_REQUESTED);
        }

        BranchRequest branchRequest = new BranchRequest(memberId, branchId);

        if (!branchRequestRepository.save(branchRequest)) {
            throw new BranchRequestException(ErrorMessage.REQUEST_DB_ERROR);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS);
    }

    /**
     * 멤버가 요청한 등록 신청 가져오기
     */
    public ServiceResult<BranchRequest> getMemberRequest(Long memberId) {
        final var branchRequest = branchRequestRepository.findByMemberId(memberId);

        if (branchRequest == null) {
            return new ServiceResult<>(ErrorMessage.REQUEST_ALREADY_REQUESTED);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, branchRequest);
    }

    /**
     * 지점 신청 취소
     */
    public ServiceResult<Void> cancelRequest(Long requestId) {
        final var findRequest = branchRequestRepository.findById(requestId);

        if (findRequest == null) {
            return new ServiceResult<>(ErrorMessage.REQUEST_NOT_EXIST);
        }

        final var updatedRow = branchRequestRepository.updateDeleteTime(requestId, TimeManager.now());

        if (updatedRow != 1) {
            throw new BranchRequestException(ErrorMessage.REQUEST_DB_ERROR);
        }

        findRequest.delete();

        return new ServiceResult<>(ErrorMessage.SUCCESS);
    }

    /**
     * 요청 수락 or 거절
     */
    public ServiceResult<Void> responseForRequest(Long requestId, AcceptType acceptType) {
        final var findRequest = branchRequestRepository.findById(requestId);

        if (findRequest == null) {
            return new ServiceResult<>(ErrorMessage.REQUEST_NOT_EXIST);
        }

        if (findRequest.getAcceptType() != null) {
            return new ServiceResult<>(ErrorMessage.REQUEST_ALREADY_EXPIRED);
        }

        final var updatedRow = branchRequestRepository.updateAccept(requestId, acceptType, TimeManager.now());

        if (updatedRow != 1) {
            throw new BranchRequestException(ErrorMessage.REQUEST_DB_ERROR);
        }

        // 수락하면 알바생으로 등록해야함
        if (acceptType == AcceptType.Accept) {
            final var findBranch = branchRepository.findById(findRequest.getBranchId());

            if (findBranch == null) {
                throw new BranchRequestException(ErrorMessage.BRANCH_NOT_EXIST);
            }

            final var findMember = memberRepository.findById(findRequest.getMemberId());

           if (findMember == null) {
               throw  new MemberException(ErrorMessage.MEMBER_NOT_EXIST);
           }

           findMember.updateBranch(findBranch);

            final var role = getRoleByName("employee");

            if (role == null) {
                throw new RoleException(ErrorMessage.ROLE_DB_ERROR);
            }

            findMember.setRole(role);
        }

        findRequest.setIsAccept(acceptType);
        // 요청 처리 완료됐으니 soft 삭제
        findRequest.delete();

        return new ServiceResult<>(ErrorMessage.SUCCESS);
    }

    /**
     * 치트용 -> 지점의 모든 요청 가져오기
     */
    public ServiceResult<Collection<BranchRequest>> getBranchRequest(Long branchId) {
        final var requests = branchRequestRepository.findByBranchId(branchId);

        if (requests.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.REQUEST_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, requests);
    }

    /**
     * 지점장이 지점의 모든 요청 가져오기
     */
    public ServiceResult<Collection<BranchRequest>> getBranchRequest(Long branchId, Long memberId) {
        final var member = memberRepository.findById(memberId);

        if (member == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        if (!member.getRole().getName().equals("master")) {
            return new ServiceResult<>(ErrorMessage.REQUEST_HAVE_NO_PERMISSION);
        }

        final var requests = branchRequestRepository.findByBranchId(branchId);

        if (requests.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.REQUEST_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, requests);
    }

    /**
     * 요청 ID로 요청 찾기
     */
    public ServiceResult<BranchRequest> getBranchRequestById(Long requestId) {
        final var request = branchRequestRepository.findById(requestId);

        if (request == null) {
            return new ServiceResult<>(ErrorMessage.REQUEST_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, request);
    }
}
