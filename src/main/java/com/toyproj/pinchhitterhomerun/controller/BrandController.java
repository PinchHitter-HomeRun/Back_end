package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.Brand;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.BrandService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    BrandService brandService;

    @ApiOperation("모든 브랜드 검색")
    @GetMapping("/all")
    public ResponseResult<Collection<Brand>> getAllBrand() {
        final var result = brandService.getAllBrand();

        return new ResponseResult<>(result);
    }

    @ApiOperation("카테고리에 속한 브랜드 검색")
    @GetMapping("/category/{categoryId}")
    public ResponseResult<Collection<Brand>> getCategoryBrand(@PathVariable("categoryId") Long categoryId) {
        final var result = brandService.getBrandByCategoryId(categoryId);

        return new ResponseResult<>(result);
    }

    @ApiOperation("컬럼 id로 브랜드 검색")
    @GetMapping("/{id}")
    public ResponseResult<Brand> getBrand(@PathVariable("id") Long id) {
        final var result = brandService.getBrandById(id);

        return new ResponseResult<>(result);
    }

    @ApiOperation("브랜드 이름으로 검색")
    @GetMapping
    public ResponseResult<Brand> getBrand(@RequestParam("name") String name) {
        final var result = brandService.getBrandByName(name);

        return new ResponseResult<>(result);
    }
}
