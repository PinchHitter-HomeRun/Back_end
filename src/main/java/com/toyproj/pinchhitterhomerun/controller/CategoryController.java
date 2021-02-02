package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.Category;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.CategoryService;
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
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // 모든 카테고리 가져오기
    @ApiOperation("모든 카테고리 검색")
    @GetMapping
    public ResponseResult<Collection<Category>> getAllCategory() {
        final var result =  categoryService.getAllCategory();

        return new ResponseResult<>(result);
    }

    // 아이디로 카테고리 가져오기
    @ApiOperation("컬럼 id로 카테고리 검색")
    @GetMapping("/{id}")
    public ResponseResult<Category> getCategory(@PathVariable("id") Long id) {
        final var result = categoryService.getCategoryById(id);

        return new ResponseResult<>(result);
    }

    // 이름으로 카테고리 가져오기
    @ApiOperation("카테고리 이름으로 카테고리 검색")
    @GetMapping("/{name}")
    public ResponseResult<Category> getCategory(@PathVariable("name") String name) {
        final var result = categoryService.getCategoryByName(name);

        return new ResponseResult<>(result);
    }
}
