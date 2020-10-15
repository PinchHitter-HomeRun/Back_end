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

    public List<Brand> getBrandByCategoryId(Long categoryId) {
        List<Brand> categories = new ArrayList<>();

        try{
            categories = brandRepository.findByCategoryId(categoryId);
        } catch (Exception e) {
            throw new BrandException("유효하지 않는 Category id 입니다.");
        }

        return categories;
    }

    public Brand getBrandByName(String name) {
        Brand brand;

        try {
            brand = brandRepository.findByName(name);
        } catch (Exception e) {
            throw new BrandException("존재하지 않는 브랜드명입니다.");
        }

        return brand;
    }

    public Brand getBrandById(Long id) {
        Brand brand;

        try {
            brand = brandRepository.findById(id);
        } catch (Exception e) {
            throw new BrandException("유효하지 않는 id 값 입니다.");
        }

        return brand;
    }
}
