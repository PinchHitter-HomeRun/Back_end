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

    // 아이디로 카테고리 가져오기
    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findById(id);

        if (category == null) {
            throw new CategoryException("유효하지 않는 id 값 입니다.");
        }

        return category;
    }

    // 카테고리 이름으로 카테고리 가져오기
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
