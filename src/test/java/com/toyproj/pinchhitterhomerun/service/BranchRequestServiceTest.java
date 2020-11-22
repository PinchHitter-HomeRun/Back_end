package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BranchRequestException;
import com.toyproj.pinchhitterhomerun.model.Branch;
import com.toyproj.pinchhitterhomerun.model.BranchRequest;
import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.repository.BranchRequestRepository;
import com.toyproj.pinchhitterhomerun.type.AcceptType;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.graalvm.compiler.asm.sparc.SPARCAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BranchRequestServiceTest {
    @Autowired
    BranchRequestService branchRequestService;

    Member haveBranchMember = new Member(543L);
    Member haveNoBranchMember = new Member(177L);

    @Test
    public void 지점_알바생_등록_신청() {
        BranchRequest newRequest = new BranchRequest(haveNoBranchMember.getId(), 10L);

        branchRequestService.requestToBranchMaster(newRequest);

        BranchRequest findRequest = branchRequestService.getMemberRequest(newRequest.getMemberId());

        Assertions.assertThat(findRequest.getId()).isEqualTo(newRequest.getId());
    }

    @Test
    public void 지점에_속한_알바생_다른_지점_등록_신청() {
        BranchRequest newRequest = new BranchRequest(haveBranchMember.getId(), 11L);

        BranchRequestException e = assertThrows(BranchRequestException.class,
                () -> branchRequestService.requestToBranchMaster(newRequest));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 역삼초교사거리점에 속해있습니다.");
    }

    @Test
    public void 지점에_연속으로_등록_신청() {
        BranchRequest newRequest = new BranchRequest(haveNoBranchMember.getId(), 10L);

        branchRequestService.requestToBranchMaster(newRequest);

        BranchRequestException e = assertThrows(BranchRequestException.class,
                () -> branchRequestService.requestToBranchMaster(newRequest));

        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.REQUEST_ALREADY_EXIST.getMessage());
    }

    @Test
    public void 다른_지점에_신청후_또_다른_지점_신청() {
        BranchRequest newRequest = new BranchRequest(haveNoBranchMember.getId(), 10L);

        branchRequestService.requestToBranchMaster(newRequest);

        BranchRequest anotherRequest = new BranchRequest(haveNoBranchMember.getId(), 11L);

        BranchRequestException e = assertThrows(BranchRequestException.class,
                () -> branchRequestService.requestToBranchMaster(anotherRequest));

        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.REQUEST_ALREADY_EXIST.getMessage());
    }

    // 지점 신청 취소
    @Test
    public void 지점_모든_요청_가져오기() {
        BranchRequest newRequest = new BranchRequest(haveNoBranchMember.getId(), 10L);

        branchRequestService.requestToBranchMaster(newRequest);
    }

    @Test
    public void 지점_신청_취소() {
        BranchRequest newRequest = new BranchRequest(haveNoBranchMember.getId(), 10L);

        branchRequestService.requestToBranchMaster(newRequest);

        branchRequestService.cancelRequest(haveNoBranchMember.getId());

        BranchRequestException e = assertThrows(BranchRequestException.class,
                () -> branchRequestService.getMemberRequest(haveNoBranchMember.getId()));

        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.REQUEST_NOT_FOUND.getMessage());
    }

    @Test
    public void 지점_신청_없이_취소() {
        BranchRequestException e = assertThrows(BranchRequestException.class,
                () -> branchRequestService.cancelRequest(haveNoBranchMember.getId()));

        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.REQUEST_NOT_FOUND.getMessage());
    }

    // 지점의 모든 요청 가져오기
    @Test
    public void 지점에_신청된_모든_요청_가져오기() {
        BranchRequest newRequest = new BranchRequest(haveNoBranchMember.getId(), 10L);

        branchRequestService.requestToBranchMaster(newRequest);

        List<BranchRequest> requests = branchRequestService.getBranchRequest(10L);

        Assertions.assertThat(requests.size()).isEqualTo(1);
    }

    @Test
    public void 요청_처리_완료된_요청_가져오기() {
        BranchRequest newRequest = new BranchRequest(haveNoBranchMember.getId(), 10L);

        branchRequestService.requestToBranchMaster(newRequest);

        List<BranchRequest> requests = branchRequestService.getBranchRequest(10L);

        for (BranchRequest request : requests) {
            if (request.getMemberId().equals(haveNoBranchMember.getId())) {
                branchRequestService.responseForRequest(request.getId(), AcceptType.Accept);
                break;
            }
        }

        BranchRequestException e = assertThrows(BranchRequestException.class,
                () -> branchRequestService.getBranchRequest(10L));

        // 요청에 대해 응답 했으니 요청을 가져올때 못가져와야한다.
        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.REQUEST_NOT_FOUND.getMessage());
    }

    // 요청 수락 or 거절
    @Test
    public void 요청_수락() {
        BranchRequest newRequest = new BranchRequest(haveNoBranchMember.getId(), 10L);

        branchRequestService.requestToBranchMaster(newRequest);

        List<BranchRequest> requests = branchRequestService.getBranchRequest(10L);

        Long id = 0L;

        for (BranchRequest request : requests) {
            if (request.getMemberId().equals(haveNoBranchMember.getId())) {
                id = request.getId();
                branchRequestService.responseForRequest(request.getId(), AcceptType.Accept);
                break;
            }
        }

        BranchRequest findRequest = branchRequestService.getBranchById(id);
        Assertions.assertThat(findRequest.getIsAccept()).isEqualTo(AcceptType.Accept);
    }

    @Test
    public void 요청_거절() {
        BranchRequest newRequest = new BranchRequest(haveNoBranchMember.getId(), 10L);

        branchRequestService.requestToBranchMaster(newRequest);

        List<BranchRequest> requests = branchRequestService.getBranchRequest(10L);

        Long id = 0L;

        for (BranchRequest request : requests) {
            if (request.getMemberId().equals(haveNoBranchMember.getId())) {
                id = request.getId();
                branchRequestService.responseForRequest(request.getId(), AcceptType.Deny);
                break;
            }
        }

        BranchRequest findRequest = branchRequestService.getBranchById(id);
        Assertions.assertThat(findRequest.getIsAccept()).isEqualTo(AcceptType.Deny);
    }

    @Test
    public void 없는_요청_수락() {
        BranchRequest newRequest = new BranchRequest(haveNoBranchMember.getId(), 10L);

        branchRequestService.requestToBranchMaster(newRequest);

        List<BranchRequest> requests = branchRequestService.getBranchRequest(10L);

        for (BranchRequest request : requests) {
            if (request.getMemberId().equals(haveNoBranchMember.getId())) {
                branchRequestService.responseForRequest(request.getId(), AcceptType.Accept);
                break;
            }
        }

        for (BranchRequest request : requests) {
            if (request.getMemberId().equals(haveNoBranchMember.getId())) {
                try {
                    branchRequestService.responseForRequest(request.getId(), AcceptType.Accept);
                } catch (Exception e) {
                    Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.REQUEST_NOT_EXIST.getMessage());
                    return;
                }
            }
        }

        Assertions.fail("fail");
    }

    @Test
    public void 없는_요청_거절() {
        BranchRequest newRequest = new BranchRequest(haveNoBranchMember.getId(), 10L);

        branchRequestService.requestToBranchMaster(newRequest);

        List<BranchRequest> requests = branchRequestService.getBranchRequest(10L);

        for (BranchRequest request : requests) {
            if (request.getMemberId().equals(haveNoBranchMember.getId())) {
                branchRequestService.responseForRequest(request.getId(), AcceptType.Deny);
                break;
            }
        }

        for (BranchRequest request : requests) {
            if (request.getMemberId().equals(haveNoBranchMember.getId())) {
                try {
                    branchRequestService.responseForRequest(request.getId(), AcceptType.Deny);
                } catch (Exception e) {
                    Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.REQUEST_NOT_EXIST.getMessage());
                    return;
                }
            }
        }

        Assertions.fail("fail");
    }
}