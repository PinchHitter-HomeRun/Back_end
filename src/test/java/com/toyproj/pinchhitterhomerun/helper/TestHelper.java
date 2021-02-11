package com.toyproj.pinchhitterhomerun.helper;

import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Brand;
import com.toyproj.pinchhitterhomerun.entity.Category;
import com.toyproj.pinchhitterhomerun.service.BranchService;
import com.toyproj.pinchhitterhomerun.service.BrandService;
import com.toyproj.pinchhitterhomerun.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestHelper {

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    BranchService branchService;

    public Category getRandomCategory() {
        final var result = categoryService.getAllCategory();
        final List<Category> categories = new ArrayList<>(result.getResponse());

        Collections.shuffle(categories);

        return categories.iterator().next();
    }

    public Brand getRandomBrand() {
        final var result = brandService.getAllBrand();
        final List<Brand> brands = new ArrayList<>(result.getResponse());

        Collections.shuffle(brands);

        return brands.iterator().next();
    }

    public Branch getRandomBranch() {
        final var result = branchService.getAllBranch();
        final List<Branch> branches = new ArrayList<>(result.getResponse());

        Collections.shuffle(branches);

        return branches.iterator().next();
    }
}
