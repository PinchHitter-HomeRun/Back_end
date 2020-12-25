package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.Category;
import com.toyproj.pinchhitterhomerun.model.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.CategoryService;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // 모든 카테고리 가져오기
    @ApiOperation("모든 카테고리 검색")
    @GetMapping
    public ResponseResult<List<Category>> getAllCategory() {
        List<Category> result =  categoryService.getAllCategory();

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 아이디로 카테고리 가져오기
    @ApiOperation("컬럼 id로 카테고리 검색")
    @GetMapping("/id/{id}")
    public ResponseResult<Category> getCategory(@PathVariable("id") Long id) {
        Category result = categoryService.getCategoryById(id);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 이름으로 카테고리 가져오기
    @ApiOperation("카테고리 이름으로 카테고리 검색")
    @GetMapping("/name/{name}")
    public ResponseResult<Category> getCategory(@PathVariable("name") String name) {
        Category result = categoryService.getCategoryByName(name);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }
}
