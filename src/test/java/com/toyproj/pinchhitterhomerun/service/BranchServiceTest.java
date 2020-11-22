package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BranchException;
import com.toyproj.pinchhitterhomerun.exception.BrandException;
import com.toyproj.pinchhitterhomerun.model.Branch;
import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.repository.BranchRepository;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
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
    BranchService branchService;

    @Test
    public void 아이디로_지점_가져오기_성공() {
        Long id = 1L;

        Branch branch = branchService.getBranchById(id);

        Assertions.assertThat(branch.getName()).isEqualTo("강남바로세움점");
    }

    @Test
    public void 아이디로_지점_가져오기_실패() {
        BranchException e = assertThrows(BranchException.class, () -> branchService.getBranchById(1000L));
        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.BRANCH_NOT_EXIST.getMessage());
    }

    @Test
    public void 주소로_검색_성공() {
        String address = "서울시 강남구 테헤란로4길46, B110호(역삼1동 826-37, 쌍용플래티넘밸류)";

        Branch branch = branchService.getBranchByAddress(address);

        Assertions.assertThat(branch.getAddress().getFullAddress()).isEqualTo(address);
    }

    @Test
    public void 지점명으로_검색_실패() {
        BranchException e = assertThrows(BranchException.class, () -> branchService.getBranchByBranchIdAndName(1L,"장원지점"));
        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.BRANCH_NOT_EXIST.getMessage());
    }

    @Test
    public void 시_구로_지점_검색() {
        List<String> branchNames = new ArrayList<String>() {
            {
                add("강남역1호점");
                add("강남역2호점");
                add("강남역3호점");
                add("강남N타워점");
                add("강남YBM점");
                add("강남갤러리점");
                add("강남리츠칼튼점");
                add("강남메트로점");
                add("강남본점");
                add("강남사랑점");
                add("강남쉐르빌점");
                add("강남시티힐점");
                add("강남쌍용점");
                add("강남아트점");
                add("강남타운점");
                add("강남태강점");
                add("강남태양점");
                add("역삼2점");
                add("역삼덕원점");
                add("역삼명진점");
                add("역삼성우점");
                add("역삼쌍마점");
                add("역삼아워홈점");
                add("역삼이담점");
                add("역삼태극점");
                add("역삼하나점");
                add("역삼행운점");
                add("역삼효성점");
                add("역삼흑룡점");
            }
        };

        List<Branch> branches = branchService.searchBranch(10L, "서울시", "강남구", null);

        for (int i = 0; i < branches.size(); i++) {
            Assertions.assertThat(branches.get(i).getName()).isEqualTo(branchNames.get(i));
        }
    }

    @Test
    public void 시_구_지점이름으로_지점_검색() {
        String branchName = "강남본점";

        List<Branch> branches = branchService.searchBranch(10L, "서울시", "강남구", "강남본점");

        Assertions.assertThat(branches.get(0).getName()).isEqualTo(branchName);
    }

    @Test
    public void 브랜드에_속한_모든_지점_가져오기_성공() {
        int resultCount = 1;
        List<Branch> branches = branchService.getBranchByBrandId(1L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 12;
        branches = branchService.getBranchByBrandId(2L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 7;
        branches = branchService.getBranchByBrandId(3L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 2;
        branches = branchService.getBranchByBrandId(4L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 3;
        branches = branchService.getBranchByBrandId(5L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 1;
        branches = branchService.getBranchByBrandId(6L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 2;
        branches = branchService.getBranchByBrandId(7L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 1;
        branches = branchService.getBranchByBrandId(8L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 22;
        branches = branchService.getBranchByBrandId(9L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 29;
        branches = branchService.getBranchByBrandId(10L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 6;
        branches = branchService.getBranchByBrandId(11L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 8;
        branches = branchService.getBranchByBrandId(12L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 0;
        branches = branchService.getBranchByBrandId(13L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 1;
        branches = branchService.getBranchByBrandId(14L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 2;
        branches = branchService.getBranchByBrandId(15L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 8;
        branches = branchService.getBranchByBrandId(16L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 2;
        branches = branchService.getBranchByBrandId(17L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 1;
        branches = branchService.getBranchByBrandId(18L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);

        resultCount = 2;
        branches = branchService.getBranchByBrandId(19L);
        Assertions.assertThat(branches.size()).isEqualTo(resultCount);
    }

    @Test
    public void 브랜드에_속한_모든_지점_가져오기_실패() {
        BrandException e = assertThrows(BrandException.class,
                () -> branchService.getBranchByBrandId(9999L));
        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.BRAND_NOT_EXIST.getMessage());
    }

    @Test
    public void 지점에_속한_모든_사용자_가져오기() {
        List<Member> members = branchService.getBranchMembers(10L);

        Assertions.assertThat(members.size()).isEqualTo(1);
    }
}