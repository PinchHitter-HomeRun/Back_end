package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.Brand;
import com.toyproj.pinchhitterhomerun.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/")
    public Map<String, List<Brand>> getAllBrand() {
        Map<String, List<Brand>> result = new HashMap<>();

        result.put("result", brandService.getAllBrand());

        return result;
    }

    @GetMapping("/category/{categoryId}")
    public Map<String, List<Brand>> getCategoryBrand(@PathVariable Long categoryId) {
        Map<String, List<Brand>> result = new HashMap<>();

        result.put("result", brandService.getBrandByCategoryId(categoryId));

        return result;
    }

    @GetMapping("/id/{id}")
    public Map<String, Brand> getBrand(@PathVariable Long id) {
        Map<String, Brand> result = new HashMap<>();

        result.put("result", brandService.getBrandById(id));

        return result;
    }

    @GetMapping("/name/{name}")
    public Map<String, Brand> getBrand(@PathVariable String name) {
        Map<String, Brand> result = new HashMap<>();

        result.put("result", brandService.getBrandByName(name));

        return result;
    }

}
