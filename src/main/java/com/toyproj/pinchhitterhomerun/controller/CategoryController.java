package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.Category;
import com.toyproj.pinchhitterhomerun.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public Map<String, List<Category>> getAllCategory() {
        Map<String, List<Category>> result = new HashMap<>();

        result.put("result", categoryService.getAllCategory());

        return result;
    }

    @GetMapping("/id/{id}")
    public Map<String, Category> getCategory(@PathVariable Long id) {
        Map<String, Category> result = new HashMap<>();

        result.put("result", categoryService.getCategoryById(id));

        return result;
    }

    @GetMapping("/name/{name}")
    public Map<String, Category> getCategory(@PathVariable String name) {
        Map<String, Category> result = new HashMap<>();

        result.put("result", categoryService.getCategoryByName(name));

        return result;
    }
}
