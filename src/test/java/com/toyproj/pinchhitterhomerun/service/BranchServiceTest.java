package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BranchException;
import com.toyproj.pinchhitterhomerun.model.Branch;
import com.toyproj.pinchhitterhomerun.repository.BranchRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BranchServiceTest {

    @Autowired
    BranchRepository branchRepository;
    @Autowired
    BranchService branchService;

    @Test
    public void 아이디로_지점_가져오기_성공() {
        String branchName = "강남바로세움점";

        Branch branch = branchRepository.findById(1L);

        Assertions.assertThat(branch.getName()).isEqualTo(branchName);
    }

    @Test
    public void 아이디로_지점_가져오기_실패() {
        BranchException e = assertThrows(BranchException.class, () -> branchService.getBranchById(1000L));
        Assertions.assertThat(e.getMessage()).isEqualTo("존재하지 않는 지점입니다.");
    }

    @Test
    public void 지점명으로_검색_성공() {
        String address = "서울특별시 강남구 테헤란로4길46, B110호(역삼1동 826-37, 쌍용플래티넘밸류)";

        Branch branch = branchRepository.findByBrandAndName(10L, "역삼태극점");

        Assertions.assertThat(branch.getAddress()).isEqualTo(address);
    }

    @Test
    public void 지점명으로_검색_실패() {
        BranchException e = assertThrows(BranchException.class, () -> branchService.getBranchByBranchIdAndName(1L,"장원지점"));
        Assertions.assertThat(e.getMessage()).isEqualTo("존재하지 않는 지점입니다.");
    }

    @Test
    public void 주소로_지점_가져오기_성공() {
        String branchName = "역삼성홍타워점";

        Branch branch = branchRepository.findByAddress("서울특별시 강남구 테헤란로 138 1층");

        Assertions.assertThat(branch.getName()).isEqualTo(branchName);
    }

    @Test
    public void 주소로_지점_가져오기_실패() {
        BranchException e = assertThrows(BranchException.class, () -> branchService.getBranchByAddress("서울특별시 강남구 테헤란로 138 2층"));
        Assertions.assertThat(e.getMessage()).isEqualTo("존재하지 않는 지점입니다.");
    }

    @Test
    public void 시_서브_주소와_검색어로_지점_검색() {
        List<String> branchNames = new ArrayList<String>() {
            {
                add("강남N타워점");
                add("강남메트로점");
                add("강남본점");
                add("강남쌍용점");
                add("강남타운점");
                add("강남태강점");
                add("역삼덕원점");
                add("역삼이담점");
                add("역삼태극점");
            }
        };
        List<Branch> branches = branchRepository.searchByKeywordWithBrandId(10L, "서울특별시", "강남구", "테헤란로");

        for (int i = 0; i < branchNames.size(); i++) {
            Assertions.assertThat(branches.get(i).getName()).isEqualTo(branchNames.get(i));
        }
    }

    @Test
    public void 브랜드에_속한_모든_지점_가져오기_성공() {
        int resultCount = 1;
        List<Branch> branches = branchRepository.findByBrandId(1L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 12;
        branches = branchRepository.findByBrandId(2L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 7;
        branches = branchRepository.findByBrandId(3L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 2;
        branches = branchRepository.findByBrandId(4L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 3;
        branches = branchRepository.findByBrandId(5L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 1;
        branches = branchRepository.findByBrandId(6L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 2;
        branches = branchRepository.findByBrandId(7L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 1;
        branches = branchRepository.findByBrandId(8L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 22;
        branches = branchRepository.findByBrandId(9L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 29;
        branches = branchRepository.findByBrandId(10L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 6;
        branches = branchRepository.findByBrandId(11L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 8;
        branches = branchRepository.findByBrandId(12L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 0;
        branches = branchRepository.findByBrandId(13L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 1;
        branches = branchRepository.findByBrandId(14L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 2;
        branches = branchRepository.findByBrandId(15L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 8;
        branches = branchRepository.findByBrandId(16L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 2;
        branches = branchRepository.findByBrandId(17L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 1;
        branches = branchRepository.findByBrandId(18L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 2;
        branches = branchRepository.findByBrandId(19L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);
    }

    @Test
    public void 브랜드에_속한_모든_지점_가져오기_실패() {
        BranchException e = assertThrows(BranchException.class,
                () -> branchService.getBranchByBrandId(9999L));
        Assertions.assertThat(e.getMessage()).isEqualTo("존재하지 않는 브랜드 입니다.");
    }

    @Test
    public void 사용자가_속한_지점_가져오기_지점있음() {
        String branchName= "역삼초교사거리점";

        Branch branch = branchRepository.findByMemberId(543L);

        Assertions.assertThat(branch.getName()).isEqualTo(branchName);
    }

    @Test
    public void 사용자가_속한_지점_가져오기_지점없음() {
        BranchException e = assertThrows(BranchException.class,
                () -> branchService.getMemberBranch(7L));
        Assertions.assertThat(e.getMessage()).isEqualTo("사용자가 속한 지점이 없습니다.");
    }
}