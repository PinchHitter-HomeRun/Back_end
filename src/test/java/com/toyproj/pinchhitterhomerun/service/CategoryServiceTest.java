package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.Brand;
import com.toyproj.pinchhitterhomerun.exception.CategoryException;
import com.toyproj.pinchhitterhomerun.entity.Category;
import com.toyproj.pinchhitterhomerun.helper.TestHelper;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest extends TestHelper {

    @Autowired
    CategoryService categoryService;

    @Test
    public void 모든_카테고리_가져오기() {
        // given
        final var testCategory = getRandomCategory();

        // when
        final var result = categoryService.getAllCategory();

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().stream().anyMatch(x -> x.getId().equals(testCategory.getId()))).isEqualTo(true);
    }

    @Test
    public void id_값으로_카테고리_가져오기_성공() {
        // given
        final var testCategory = getRandomCategory();

        // when
        final var result = categoryService.getCategoryById(testCategory.getId());

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().getId()).isEqualTo(testCategory.getId());
    }

    @Test
    public void id_값으로_카테고리_가져오기_실패() {
        // given
        final var testCategoryId = 0L;

        // when
        final var result = categoryService.getCategoryById(testCategoryId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.CATEGORY_NOT_FOUND.getMessage());
    }

    @Test
    public void 이름으로_카테고리_가져오기_성공() {
        // given
        final var testCategory = getRandomCategory();

        // when
        final var result = categoryService.getCategoryByName(testCategory.getName());

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().getId()).isEqualTo(testCategory.getId());
    }

    @Test
    public void 이름으로_카테고리_가져오기_실패() {
        // given
        final var testCategoryName = "실패하자";

        // when
        final var result = categoryService.getCategoryByName(testCategoryName);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.CATEGORY_NOT_FOUND.getMessage());
    }

}