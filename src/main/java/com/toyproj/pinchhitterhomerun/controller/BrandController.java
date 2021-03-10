package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.Brand;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.BrandService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ResponseBody
    @GetMapping("/all")
    public ResponseResult<Collection<Brand>> getAllBrand() {
        final var result = brandService.getAllBrand();

        return new ResponseResult<>(result);
    }

    @ApiOperation("카테고리에 속한 브랜드 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "카테고리 ID\n" +
                    "1 - 카페\n" +
                    "2 - 패스트 푸드\n" +
                    "3 - 편의점\n" +
                    "4 - 영화관\n" +
                    "5 - 뷰티", required = true, dataType = "long", example = "0")
    })
    @ResponseBody
    @GetMapping("/category/{categoryId}")
    public ResponseResult<Collection<Brand>> getCategoryBrand(@PathVariable("categoryId") Long categoryId) {
        final var result = brandService.getBrandByCategoryId(categoryId);

        return new ResponseResult<>(result);
    }

    @ApiOperation("컬럼 id로 브랜드 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "브랜드 ID\n" +
                    "1 - 탐앤탐스\n" +
                    "2 - 스타벅스\n" +
                    "3 - 투썸플레이스\n" +
                    "4 - 엔제리너스\n" +
                    "5 - 할리스\n" +
                    "6 - 맥도날드\n" +
                    "7 - 롯데리아\n" +
                    "8 - 버거킹\n" +
                    "9 - CU\n" +
                    "10 - GS25\n" +
                    "11 - 미니스톱\n" +
                    "12 - 이마트24\n" +
                    "13 - 롯데시네마\n" +
                    "14 - CGV\n" +
                    "15 - 메가박스\n" +
                    "16 - 올리브영\n" +
                    "17 - 아리따움\n" +
                    "18 - 롭스\n" +
                    "19 - 토니모리", required = true, dataType = "long", example = "0")
    })
    @ResponseBody
    @GetMapping("/{brandId}")
    public ResponseResult<Brand> getBrand(@PathVariable("brandId") Long id) {
        final var result = brandService.getBrandById(id);

        return new ResponseResult<>(result);
    }

    @ApiOperation("브랜드 이름으로 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "브랜드 이름\n" +
                    "1 - 탐앤탐스\n" +
                    "2 - 스타벅스\n" +
                    "3 - 투썸플레이스\n" +
                    "4 - 엔제리너스\n" +
                    "5 - 할리스\n" +
                    "6 - 맥도날드\n" +
                    "7 - 롯데리아\n" +
                    "8 - 버거킹\n" +
                    "9 - CU\n" +
                    "10 - GS25\n" +
                    "11 - 미니스톱\n" +
                    "12 - 이마트24\n" +
                    "13 - 롯데시네마\n" +
                    "14 - CGV\n" +
                    "15 - 메가박스\n" +
                    "16 - 올리브영\n" +
                    "17 - 아리따움\n" +
                    "18 - 롭스\n" +
                    "19 - 토니모리", required = true, dataType = "String")
    })
    @ResponseBody
    @GetMapping
    public ResponseResult<Brand> getBrand(@RequestParam("name") String name) {
        final var result = brandService.getBrandByName(name);

        return new ResponseResult<>(result);
    }
}
