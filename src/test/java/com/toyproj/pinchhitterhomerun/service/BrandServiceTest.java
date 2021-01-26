package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BrandException;
import com.toyproj.pinchhitterhomerun.entity.Brand;
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
class BrandServiceTest {

    @Autowired
    BrandService brandService;

    //given
    private final List<Brand> originList = new ArrayList<Brand>() {
        {
            add(new Brand(1L, new Category(1L, ""), "탐앤탐스"));
            add(new Brand(2L, new Category(1L, ""), "스타벅스"));
            add(new Brand(3L, new Category(1L, ""), "투썸플레이스"));
            add(new Brand(4L, new Category(1L, ""), "엔제리너스"));
            add(new Brand(5L, new Category(1L, ""), "할리스"));
            add(new Brand(6L, new Category(2L, ""), "맥도날드"));
            add(new Brand(7L, new Category(2L, ""), "롯데리아"));
            add(new Brand(8L, new Category(2L, ""), "버거킹"));
            add(new Brand(9L, new Category(3L, ""), "CU"));
            add(new Brand(10L, new Category(3L, ""), "GS25"));
            add(new Brand(11L, new Category(3L, ""), "미니스톱"));
            add(new Brand(12L, new Category(3L, ""), "이마트24"));
            add(new Brand(13L, new Category(4L, ""), "롯데시네마"));
            add(new Brand(14L, new Category(4L, ""), "CGV"));
            add(new Brand(15L, new Category(4L, ""), "메가박스"));
            add(new Brand(16L, new Category(5L, ""), "올리브영"));
            add(new Brand(17L, new Category(5L, ""), "아리따움"));
            add(new Brand(18L, new Category(5L, ""), "롭스"));
            add(new Brand(19L, new Category(5L, ""), "토니모리"));
        }
    };

    @Test
    public void 모든_브랜드_가져오기() {
        List<Brand> brands = brandService.getAllBrand();

        for (int i = 0; i < brands.size(); i++) {
            Assertions.assertThat(brands.get(i).getCategory().getId()).isEqualTo(originList.get(i).getCategory().getId());
            Assertions.assertThat(brands.get(i).getId()).isEqualTo(originList.get(i).getId());
            Assertions.assertThat(brands.get(i).getName()).isEqualTo(originList.get(i).getName());
        }
    }

    @Test
    public void 카테고리_id로_브랜드_가져오기_성공() {
        List<Brand> brands = brandService.getBrandByCategoryId(1L);

        for (int i = 0; i < brands.size(); i++) {
            Assertions.assertThat(brands.get(i).getCategory().getId()).isEqualTo(originList.get(i).getCategory().getId());
            Assertions.assertThat(brands.get(i).getId()).isEqualTo(originList.get(i).getId());
            Assertions.assertThat(brands.get(i).getName()).isEqualTo(originList.get(i).getName());
        }
    }

    @Test
    public void 카테고리_id로_브랜드_가져오기_실패() {
        BrandException e = assertThrows(BrandException.class, () -> brandService.getBrandByCategoryId(6L));

        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.BRAND_NOT_EXIST.getMessage());
    }

    @Test
    public void id로_브랜드_가져오기_성공() {
        for (long i = 1; i <= originList.size(); i++) {
            Brand brand = brandService.getBrandById(i);

            Assertions.assertThat(brand.getCategory().getId()).isEqualTo(originList.get((int) i - 1).getCategory().getId());
            Assertions.assertThat(brand.getId()).isEqualTo(originList.get((int) i - 1).getId());
            Assertions.assertThat(brand.getName()).isEqualTo(originList.get((int) i - 1).getName());
        }
    }

    @Test
    public void id로_브랜드_가져오기_실패() {
        BrandException e = assertThrows(BrandException.class, () -> brandService.getBrandById(20L));

        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.BRAND_NOT_EXIST.getMessage());
    }

    @Test
    public void 이름으로_브랜드_가져오기_성공() {
        for (Brand element : originList) {
            Brand brand = brandService.getBrandByName(element.getName());

            Assertions.assertThat(brand.getCategory().getId()).isEqualTo(element.getCategory().getId());
            Assertions.assertThat(brand.getId()).isEqualTo(element.getId());
            Assertions.assertThat(brand.getName()).isEqualTo(element.getName());
        }
    }

    @Test
    public void 이름으로_브랜드_가져오기_실패() {
        BrandException e = assertThrows(BrandException.class, () -> brandService.getBrandByName("오류"));

        Assertions.assertThat(e.getMessage()).isEqualTo(ErrorMessage.BRAND_NOT_EXIST.getMessage());
    }
}