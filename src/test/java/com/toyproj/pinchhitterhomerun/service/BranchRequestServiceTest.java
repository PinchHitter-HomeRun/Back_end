package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BranchRequestException;
import com.toyproj.pinchhitterhomerun.model.Branch;
import com.toyproj.pinchhitterhomerun.model.BranchRequest;
import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.repository.BranchRequestRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BranchRequestServiceTest {
    @Autowired
    BranchRequestRepository branchRequestRepository;
    @Autowired
    BranchRequestService branchRequestService;

    Member haveBranchMember = new Member(543L);
    Member haveNoBranchMember = new Member(177L);

    @Test
    public void 지점_알바생_등록_신청() {
        BranchRequest request = new BranchRequest(haveNoBranchMember.getId(), 10L);

        branchRequestRepository.save(request);

        BranchRequest findRequest = branchRequestRepository.findByMemberId(request.getMemberId());

        Assertions.assertThat(findRequest.getId()).isEqualTo(request.getId());
    }

    @Test
    public void 지점에_속한_알바생_다른_지점_등록_신청() {
        BranchRequest newRequest = new BranchRequest(haveBranchMember.getId(), 11L);

        BranchRequestException e = assertThrows(BranchRequestException.class,
                () -> branchRequestService.requestToBranchMaster(newRequest));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 역삼초교사거리점에 속해있습니다.");
    }

    // 지점 신청 취소


    // 요청 수락 or 거절


    // 지점의 모든 요청 가져오기


}