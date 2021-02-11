package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.helper.TestAccountManager;
import com.toyproj.pinchhitterhomerun.helper.TestHelper;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BranchServiceTest extends TestHelper {

    @Autowired
    BranchService branchService;

    @Autowired
    BrandService brandService;

    @Autowired
    MemberService memberService;

    @Autowired
    TestAccountManager testAccountManager;

    private class BranchSet {
        public Branch getRandomBranchByBrand(Long brandId) {
            final var result = branchService.getBranchByBrandId(brandId);

            return result.getResponse().iterator().next();
        }
    }

    BranchSet branchSet = new BranchSet();

    @Test
    public void 아이디로_지점_가져오기_성공() {
        // given
        final var testBranch = getRandomBranch();

        // when
        final var branch = branchService.getBranchById(testBranch.getId());

        // then
        assertThat(branch.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(branch.getResponse().getName()).isEqualTo(testBranch.getName());
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
    @Transactional
    public void 주소로_검색_성공() {
        // given
        final var testBranch = getRandomBranch();
        final String testAddress = testBranch.getAddress().getFullAddress();

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
        final var testBrand = getRandomBrand();
        final var testBranch= branchSet.getRandomBranchByBrand(testBrand.getId());
        
        // when
        final var branch = branchService.getBranchByBrandIdAndName(testBrand.getId(), testBranch.getName());

        // then
        assertThat(branch.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(branch.getResponse().getBrand().getId()).isEqualTo(testBrand.getId());
        assertThat(branch.getResponse().getName()).isEqualTo(testBranch.getName());
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
        final var testBrand = getRandomBrand();
        final var testBranch = branchSet.getRandomBranchByBrand(testBrand.getId());
        final String testCity = testBranch.getAddress().getCity();
        final String testGu = testBranch.getAddress().getGu();
        final String testBranchName = testBranch.getName();

        // when
        final var branches = branchService.searchBranch(testBrand.getId(), testCity, testGu, testBranchName);

        // then
        assertThat(branches.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(branches.getResponse().iterator().next().getAddress().getCity()).isEqualTo(testCity);
        assertThat(branches.getResponse().iterator().next().getAddress().getGu()).isEqualTo(testGu);
        assertThat(branches.getResponse().iterator().next().getName()).isEqualTo(testBranchName);
    }

    @Test
    public void 시_구로_지점_검색_실패() {
        // given
        final var testBrand = getRandomBrand();
        final String testCity = "아무개시";
        final String testGu = "아무개구";
        final String testBranchName = "아무개점";

        // when
        final var branches = branchService.searchBranch(testBrand.getId(), testCity, testGu, testBranchName);

        // then
        assertThat(branches.getResult()).isEqualTo(ErrorMessage.BRANCH_NOT_EXIST.getMessage());
    }

    @Test
    public void 브랜드에_속한_모든_지점_가져오기_성공() {
        // given
        final var testBrand = getRandomBrand();

        // when
        final var branches = branchService.getBranchByBrandId(testBrand.getId());

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