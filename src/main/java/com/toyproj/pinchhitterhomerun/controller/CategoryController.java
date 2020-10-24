package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.Category;
import com.toyproj.pinchhitterhomerun.service.CategoryService;
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
    @GetMapping("/")
    public Map<String, List<Category>> getAllCategory() {
        Map<String, List<Category>> result = new HashMap<>();

        result.put("result", categoryService.getAllCategory());

        return result;
    }

    // 아이디로 카테고리 가져오기
    @GetMapping("/id/{id}")
    public Map<String, Category> getCategory(@PathVariable Long id) {
        Map<String, Category> result = new HashMap<>();

        result.put("result", categoryService.getCategoryById(id));

        return result;
    }

    // 이름으로 카테고리 가져오기
    @GetMapping("/name/{name}")
    public Map<String, Category> getCategory(@PathVariable String name) {
        Map<String, Category> result = new HashMap<>();

        result.put("result", categoryService.getCategoryByName(name));

        return result;
    }
}
