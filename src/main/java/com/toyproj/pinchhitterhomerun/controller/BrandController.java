package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.Brand;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.BrandService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    // 모든 브랜드 가져오기
    @ApiOperation("모든 브랜드 검색")
    @GetMapping
    public ResponseResult<Collection<Brand>> getAllBrand() {
        final var result = brandService.getAllBrand();

        return new ResponseResult<>(result);
    }

    // 카테고리에 속한 브랜드들 가져오기
    @ApiOperation("카테고리에 속한 브랜드 검색")
    @GetMapping("/category/{categoryId}")
    public ResponseResult<Collection<Brand>> getCategoryBrand(@PathVariable("categoryId") Long categoryId) {
        final var result = brandService.getBrandByCategoryId(categoryId);

        return new ResponseResult<>(result);
    }

    // 아이디로 브랜드 가져오기
    @ApiOperation("컬럼 id로 브랜드 검색")
    @GetMapping("/{id}")
    public ResponseResult<Brand> getBrand(@PathVariable("id") Long id) {
        final var result = brandService.getBrandById(id);

        return new ResponseResult<>(result);
    }

    // 이름으로 브랜드 가져오기
    @ApiOperation("브랜드 이름으로 검색")
    @GetMapping("/{name}")
    public ResponseResult<Brand> getBrand(@PathVariable("name") String name) {
        final var result = brandService.getBrandByName(name);

        return new ResponseResult<>(result);
    }

}
