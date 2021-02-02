package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BrandException;
import com.toyproj.pinchhitterhomerun.entity.Brand;
import com.toyproj.pinchhitterhomerun.entity.Category;
import com.toyproj.pinchhitterhomerun.repository.CategoryRepository;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BrandServiceTest {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    private class BrandSet {
        public Brand getRandomBrand() {
            final var result = brandService.getAllBrand();
            final List<Brand> brands = new ArrayList<>(result.getResponse());

            Collections.shuffle(brands);

            return brands.iterator().next();
        }

        public Category getRandomCategory() {
            final var result = categoryService.getAllCategory();
            final List<Category> categories = new ArrayList<>(result.getResponse());

            Collections.shuffle(categories);

            return categories.iterator().next();
        }
    }

    BrandSet brandSet = new BrandSet();

    @Test
    public void 모든_브랜드_가져오기_성공() {
        // given
        final var testBrand = brandSet.getRandomBrand();

        // when
        final var result = brandService.getAllBrand();

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().contains(testBrand)).isEqualTo(true);
    }

    @Test
    public void 카테고리_id로_브랜드_가져오기_성공() {
        // given
        final var testCategory = brandSet.getRandomCategory();

        // when
        final var result = brandService.getBrandByCategoryId(testCategory.getId());

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isNotEqualTo(0);
    }

    @Test
    public void 카테고리_id로_브랜드_가져오기_실패() {
        // given
        final var testCategory = 0L;

        // when
        final var result = brandService.getBrandByCategoryId(testCategory);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BRAND_NOT_FOUND.getMessage());
    }

    @Test
    public void id로_브랜드_가져오기_성공() {
        // given
        final var testBrand = brandSet.getRandomBrand();

        // when
        final var result = brandService.getBrandById(testBrand.getId());

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().equals(testBrand)).isEqualTo(true);
    }

    @Test
    public void id로_브랜드_가져오기_실패() {
        // given
        final var testBrandId = 0L;

        // when
        final var result = brandService.getBrandById(testBrandId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BRAND_NOT_EXIST.getMessage());
    }

    @Test
    public void 이름으로_브랜드_가져오기_성공() {
        // given
        final var testBrand = brandSet.getRandomBrand();

        // when
        final var result = brandService.getBrandByName(testBrand.getName());

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().equals(testBrand)).isEqualTo(true);
    }

    @Test
    public void 이름으로_브랜드_가져오기_실패() {
        // given
        final var testBrandName = "실패하자";

        // when
        final var result = brandService.getBrandByName(testBrandName);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.BRAND_NOT_EXIST.getMessage());
    }
}