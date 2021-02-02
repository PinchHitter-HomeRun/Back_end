package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.exception.CategoryException;
import com.toyproj.pinchhitterhomerun.entity.Category;
import com.toyproj.pinchhitterhomerun.repository.CategoryRepository;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ServiceResult<Collection<Category>> getAllCategory() {
        final var category = categoryRepository.findAll();

        if (category.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.CATEGORY_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, category);
    }

    // 아이디로 카테고리 가져오기
    public ServiceResult<Category> getCategoryById(Long id) {
        final var category = categoryRepository.findById(id);

        if (category == null) {
            return new ServiceResult<>(ErrorMessage.CATEGORY_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, category);
    }

    // 카테고리 이름으로 카테고리 가져오기
    public ServiceResult<Category> getCategoryByName(String name) {
        final var category = categoryRepository.findByName(name);

        if (category == null) {
            return new ServiceResult<>(ErrorMessage.CATEGORY_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, category);
    }
}
