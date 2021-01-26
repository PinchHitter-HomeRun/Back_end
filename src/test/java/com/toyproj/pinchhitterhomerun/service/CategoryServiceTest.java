package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.CategoryException;
import com.toyproj.pinchhitterhomerun.entity.Category;
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
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    //given
    private final List<Category> originList = new ArrayList<Category>() {
        {
            add(new Category(1L, "카페"));
            add(new Category(2L, "패스트 푸드"));
            add(new Category(3L, "편의점"));
            add(new Category(4L, "영화관"));
            add(new Category(5L, "뷰티"));
        }
    };

    @Test
    public void 모든_카테고리_가져오기() {
        List<Category> categories = categoryService.getAllCategory();

        for (int i = 0; i < categories.size(); i++) {
            Assertions.assertThat(categories.get(i).getId()).isEqualTo(originList.get(i).getId());
            Assertions.assertThat(categories.get(i).getName()).isEqualTo(originList.get(i).getName());
        }
    }

    @Test
    public void id_값으로_카테고리_가져오기_성공() {
        for (long i = 1L; i <= originList.size(); i++) {
            Category category = categoryService.getCategoryById(i);

            Assertions.assertThat(category.getId()).isEqualTo(originList.get((int) i - 1).getId());
            Assertions.assertThat(category.getName()).isEqualTo(originList.get((int) i - 1).getName());
        }
    }

    @Test
    public void id_값으로_카테고리_가져오기_실패() {
        CategoryException e = assertThrows(CategoryException.class, () -> categoryService.getCategoryById(6L));

        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.CATEGORY_NOT_EXIST.getMessage());
    }

    @Test
    public void 이름으로_카테고리_가져오기_성공() {
        for (Category element : originList) {
            Category category = categoryService.getCategoryByName(element.getName());

            Assertions.assertThat(category.getId()).isEqualTo(element.getId());
            Assertions.assertThat(category.getName()).isEqualTo(element.getName());
        }
    }

    @Test
    public void 이름으로_카테고리_가져오기_실패() {
        CategoryException e = assertThrows(CategoryException.class, () -> categoryService.getCategoryByName("오류"));

        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.CATEGORY_NOT_EXIST.getMessage());
    }

}