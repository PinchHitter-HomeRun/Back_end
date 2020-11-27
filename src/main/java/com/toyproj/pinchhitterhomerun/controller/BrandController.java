package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.Brand;
import com.toyproj.pinchhitterhomerun.model.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.BrandService;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    // 모든 브랜드 가져오기
    @GetMapping("/")
    public ResponseResult<List<Brand>> getAllBrand() {
        List<Brand> result = brandService.getAllBrand();

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 카테고리에 속한 브랜드들 가져오기
    @GetMapping("/category/{categoryId}")
    public ResponseResult<List<Brand>> getCategoryBrand(@PathVariable Long categoryId) {

        List<Brand> result = brandService.getBrandByCategoryId(categoryId);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 아이디로 브랜드 가져오기
    @GetMapping("/id/{id}")
    public ResponseResult<Brand> getBrand(@PathVariable Long id) {
        Brand result = brandService.getBrandById(id);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 이름으로 브랜드 가져오기
    @GetMapping("/name/{name}")
    public ResponseResult<Brand> getBrand(@PathVariable String name) {
        Brand result = brandService.getBrandByName(name);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

}
