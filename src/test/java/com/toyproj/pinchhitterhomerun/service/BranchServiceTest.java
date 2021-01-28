package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BranchException;
import com.toyproj.pinchhitterhomerun.exception.BrandException;
import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BranchServiceTest {

    @Autowired
    BranchService branchService;

    @Autowired
    BrandService brandService;

    @Autowired
    MemberService memberService;

    @Autowired
    TestAccountManager testAccountManager;

    @Test
    public void 아이디로_지점_가져오기_성공() {
        // given
        final var testId = 1L;

        // when
        final var branch = branchService.getBranchById(testId);

        // then
        assertThat(branch.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(branch.getResponse().getName()).isEqualTo("강남바로세움점");
    }

    @Test
    public void 아이디로_지점_가져오기_실패() {
        // given
        final var testId = 0L;

        // when
        final var branch = branchService.getBranchById(testId);

        // then
        assertThat(branch.getResult()).isEqualTo(ErrorMessage.BRANCH_NOT_EXIST.getMessage());
    }

    @Test
    public void 주소로_검색_성공() {
        // given
        final String testAddress = "서울시 강남구 테헤란로4길46, B110호(역삼1동 826-37, 쌍용플래티넘밸류)";

        // when
        final var branch = branchService.getBranchByAddress(testAddress);

        // then
        assertThat(branch.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(branch.getResponse().getAddress().getFullAddress()).isEqualTo(testAddress);
    }

    @Test
    public void 주소로_검색_실패() {
        // given
        final String testAddress = "서울시 우리집 우리동네";

        // when
        final var branch = branchService.getBranchByAddress(testAddress);

        // then
        assertThat(branch.getResult()).isEqualTo(ErrorMessage.BRANCH_NOT_EXIST.getMessage());
    }

    @Test
    public void 브랜드_ID와_지점명으로_검색_성공() {
        // given
        final var testBrandId = 1L;
        final var testBranchName= "강남바로세움점";
        
        // when
        final var branch = branchService.getBranchByBrandIdAndName(testBrandId, testBranchName);

        // then
        assertThat(branch.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(branch.getResponse().getBrand().getId()).isEqualTo(testBrandId);
        assertThat(branch.getResponse().getName()).isEqualTo(testBranchName);
    }

    @Test
    public void 브랜드_ID와_지점명으로_검색_실패() {
        // given
        final var testBrandId = 2L;
        final var testBranchName= "강남바로세움점";

        // when
        final var branch = branchService.getBranchByBrandIdAndName(testBrandId, testBranchName);

        // then
        assertThat(branch.getResult()).isEqualTo(ErrorMessage.BRANCH_NOT_EXIST.getMessage());

    }

    @Test
    @Transactional
    public void 시_구로_지점_검색_성공() {
        // given
        final var testBrand = brandService.getBrandById(10L);
        assertThat(testBrand).isNotNull();

        final var testBrandId= testBrand.getId();
        final String testCity = "서울시";
        final String testGu = "강남구";
        final String testBranchName = "역삼흑룡점";

        // when
        final var branches = branchService.searchBranch(testBrandId, testCity, testGu, testBranchName);

        // then
        assertThat(branches.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(branches.getResponse().iterator().next().getAddress().getCity()).isEqualTo(testCity);
        assertThat(branches.getResponse().iterator().next().getAddress().getGu()).isEqualTo(testGu);
        assertThat(branches.getResponse().iterator().next().getName()).isEqualTo(testBranchName);
    }

    @Test
    public void 시_구로_지점_검색_실패() {
        // given
        final var testBrand = brandService.getBrandById(10L);
        assertThat(testBrand).isNotNull();

        final var testBrandId= testBrand.getId();
        final String testCity = "아무개시";
        final String testGu = "아무개구";
        final String testBranchName = "아무개점";

        // when
        final var branches = branchService.searchBranch(testBrandId, testCity, testGu, testBranchName);

        // then
        assertThat(branches.getResult()).isEqualTo(ErrorMessage.BRANCH_NOT_EXIST.getMessage());
    }

    @Test
    @Transactional
    public void 시_구_지점이름으로_지점_검색_성공() {
        // given
        final var testBrand = brandService.getBrandById(10L);
        assertThat(testBrand).isNotNull();

        final var testBrandId= testBrand.getId();
        final String testCity = "서울시";
        final String testGu = "강남구";
        final String testBranchName = "역삼흑룡점";

        // when
        final var branches
                = branchService.searchBranch(testBrandId, testCity, testGu, testBranchName);

        // then
        assertThat(branches.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(branches.getResponse().iterator().next().getAddress().getCity()).isEqualTo(testCity);
        assertThat(branches.getResponse().iterator().next().getAddress().getGu()).isEqualTo(testGu);
        assertThat(branches.getResponse().iterator().next().getName()).isEqualTo(testBranchName);    }

    @Test
    public void 브랜드에_속한_모든_지점_가져오기_성공() {
        // given
        final var testBrand = brandService.getBrandById(1L);
        final var testBrandId = testBrand.getId();

        // when
        final var branches = branchService.getBranchByBrandId(testBrandId);

        // then
        assertThat(branches.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(branches.getResponse().size()).isGreaterThan(0);
    }

    @Test
    public void 브랜드에_속한_모든_지점_가져오기_실패() {
        // given
        final var testBrandId = 999L;

        // when
        final var branches = branchService.getBranchByBrandId(testBrandId);

        // then
        assertThat(branches.getResult()).isEqualTo(ErrorMessage.BRANCH_NOT_EXIST.getMessage());
    }

    @Test
    public void 지점에_속한_모든_사용자_가져오기() {
        // given
        testAccountManager.process();
        testAccountManager.setBranch();

        final var testBranch = TestAccountManager.testMember.getBranch();
        final var testBranchId = testBranch.getId();
        final var testMember = memberService.getMemberInfo(TestAccountManager.testMember.getId());
        assertThat(testMember.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var members = branchService.getBranchMembers(testBranchId);

        // then
        assertThat(members.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var checkMember = members
                .getResponse()
                .stream()
                .filter(x -> x.getId().equals(testMember.getResponse().getId()))
                .findFirst()
                .orElse(null);
        assertThat(checkMember).isNotNull();
        assertThat(checkMember.getId()).isEqualTo(testMember.getResponse().getId());
    }

    @Test
    public void 지점에_속한_모든_사용자_가져오기_알바_없음() {
        // given
        final var testBranch = branchService.getBranchById(3L);
        assertThat(testBranch.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var testBranchId = testBranch.getResponse().getId();

        // when
        final var members = branchService.getBranchMembers(testBranchId);

        // then
        assertThat(members.getResult()).isEqualTo(ErrorMessage.BRANCH_EMPTY_EMPLOYEE.getMessage());
    }
}