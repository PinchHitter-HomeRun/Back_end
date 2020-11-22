package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BranchException;
import com.toyproj.pinchhitterhomerun.exception.BranchRequestException;
import com.toyproj.pinchhitterhomerun.model.BranchRequest;
import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.repository.BranchRequestRepository;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import com.toyproj.pinchhitterhomerun.type.AcceptType;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BranchRequestService {

    private final BranchRequestRepository branchRequestRepository;
    private final MemberRepository memberRepository;

    // 지점에 알바생 등록 신청
    public void requestToBranchMaster(BranchRequest request) {

        Member findMember = memberRepository.findById(request.getMemberId());

        if (findMember.getBranch() != null) {
            String branchName = findMember.getBranch().getName();
            throw new BranchRequestException("이미 " + branchName + "에 속해있습니다.");
        }

        try {
            // 중복 요청이 있는지 확인
            branchRequestRepository.findByMemberId(request.getMemberId());
        } catch (Exception e) {
            // 중복이 없으면 EmptyResultDataAccessException
            branchRequestRepository.save(request);
            return;
        }

        throw new BranchRequestException(ErrorMessage.REQUEST_ALREADY_EXIST);
    }

    // 멤버가 요청한 등록 신청 가져오기
    public BranchRequest getMemberRequest(Long memberId) {
        BranchRequest branchRequest;

        try {
            branchRequest = branchRequestRepository.findByMemberId(memberId);
        } catch (Exception e) {
            throw new BranchRequestException(ErrorMessage.REQUEST_NOT_FOUND);
        }

        return branchRequest;
    }

    // 지점 신청 취소
    public void cancelRequest(Long memberId) {
        BranchRequest findRequest;

        try {
            findRequest = branchRequestRepository.findByMemberId(memberId);
        } catch (Exception e) {
            throw new BranchRequestException(ErrorMessage.REQUEST_NOT_FOUND);
        }

        findRequest.delete();

        branchRequestRepository.save(findRequest);
    }

    // 요청 수락 or 거절
    public void responseForRequest(Long id, AcceptType acceptType) {
        BranchRequest findRequest;

        try {
            findRequest = branchRequestRepository.findById(id);
        } catch (Exception e) {
            throw new BranchRequestException(ErrorMessage.REQUEST_NOT_EXIST);
        }

        if (findRequest.getIsAccept() == null) {
            findRequest.setIsAccept(acceptType);
        } else {
            throw new BranchRequestException(ErrorMessage.REQUEST_NOT_EXIST);
        }

        branchRequestRepository.save(findRequest);
    }

    // 지점의 모든 요청 가져오기
    public List<BranchRequest> getBranchRequest(Long branchId) {
        List<BranchRequest> requests = branchRequestRepository.findByBranchId(branchId);

        if (requests.size() == 0) {
            throw new BranchRequestException(ErrorMessage.REQUEST_NOT_FOUND);
        }

        return requests;
    }

    public BranchRequest getBranchById(Long id) {
        BranchRequest request = branchRequestRepository.findById(id);

        if (request == null) {
            throw new BranchRequestException(ErrorMessage.REQUEST_NOT_EXIST);
        }

        return request;
    }
}
