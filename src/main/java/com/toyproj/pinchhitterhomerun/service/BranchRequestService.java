package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BranchException;
import com.toyproj.pinchhitterhomerun.exception.BranchRequestException;
import com.toyproj.pinchhitterhomerun.model.BranchRequest;
import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.repository.BranchRequestRepository;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import com.toyproj.pinchhitterhomerun.type.AcceptType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new BranchRequestException("이미 " + findMember.getBranch().getName() + "에 속해있습니다.");
        }

        try {
            BranchRequest findRequest = branchRequestRepository.findByMemberId(request.getMemberId());
        } catch (Exception e) {
            branchRequestRepository.save(request);
            return;
        }

        throw new BranchRequestException("이미 신청하였습니다.");
    }

    // 지점 신청 취소
    public void cancelRequest(Long memberId) {
        BranchRequest findRequest = branchRequestRepository.findByMemberId(memberId);

        findRequest.softDelete();

        branchRequestRepository.save(findRequest);
    }

    // 요청 수락 or 거절
    public void responseForRequest(Long id, AcceptType acceptType) {
        BranchRequest findRequest;

        try {
            findRequest = branchRequestRepository.findById(id);
        } catch (Exception e) {
            throw new BranchException("존재하지 않는 요청입니다.");
        }

        findRequest.setIsAccept(acceptType);

        branchRequestRepository.save(findRequest);
    }

    // 지점의 모든 요청 가져오기
    public List<BranchRequest> getBranchRequest(Long branchId) {
        List<BranchRequest> requests = branchRequestRepository.findByBranchId(branchId);

        if (requests.size() == 0) {
            throw new BranchException("등록 요청이 없습니다.");
        }

        return requests;
    }
}
