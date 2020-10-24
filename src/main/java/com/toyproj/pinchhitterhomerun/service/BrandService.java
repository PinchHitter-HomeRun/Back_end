package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BrandException;
import com.toyproj.pinchhitterhomerun.model.Brand;
import com.toyproj.pinchhitterhomerun.model.Category;
import com.toyproj.pinchhitterhomerun.repository.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }

    // 카테고리 아이디로 브랜드 가져오기
    public List<Brand> getBrandByCategoryId(Long categoryId) {
        List<Brand> categories;

        categories = brandRepository.findByCategoryId(categoryId);

        if (categories.size() < 1) {
            throw new BrandException("유효하지 않는 Category id 입니다.");
        }

        return categories;
    }

    // 브랜드 이름으로 브랜드 가져오기
    public Brand getBrandByName(String name) {
        Brand brand;

        try {
            brand = brandRepository.findByName(name);
        } catch (Exception e) {
            throw new BrandException("존재하지 않는 브랜드명입니다.");
        }

        return brand;
    }

    // 아이디로 브랜드 가져오기
    public Brand getBrandById(Long id) {
        Brand brand = brandRepository.findById(id);

        if (brand == null) {
            throw new BrandException("유효하지 않는 id 값 입니다.");
        }

        return brand;
    }
}
