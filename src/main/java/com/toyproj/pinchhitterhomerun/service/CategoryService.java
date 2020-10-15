package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.CategoryException;
import com.toyproj.pinchhitterhomerun.model.Category;
import com.toyproj.pinchhitterhomerun.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Category category;

        try {
            category = categoryRepository.findById(id);
        } catch (Exception e) {
            throw new CategoryException("유효하지 않는 id 값 입니다.");
        }

        return category;
    }

    public Category getCategoryByName(String name) {
        Category category;

        try{
            category = categoryRepository.findByName(name);
        } catch (Exception e) {
            throw new CategoryException("유효하지 않는 name 값 입니다.");
        }

        return category;
    }
}
