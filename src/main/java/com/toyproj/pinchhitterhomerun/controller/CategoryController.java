package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.Category;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @ApiOperation("모든 카테고리 검색")
    @ResponseBody
    @GetMapping("/all")
    public ResponseResult<Collection<Category>> getAllCategory() {
        final var result =  categoryService.getAllCategory();

        return new ResponseResult<>(result);
    }

    @ApiOperation("컬럼 id로 카테고리 검색")
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseResult<Category> getCategory(@PathVariable("id") Long id) {
        final var result = categoryService.getCategoryById(id);

        return new ResponseResult<>(result);
    }

    @ApiOperation("카테고리 이름으로 카테고리 검색")
    @ResponseBody
    @GetMapping
    public ResponseResult<Category> getCategory(@RequestParam("name") String name) {
        final var result = categoryService.getCategoryByName(name);

        return new ResponseResult<>(result);
    }
}
