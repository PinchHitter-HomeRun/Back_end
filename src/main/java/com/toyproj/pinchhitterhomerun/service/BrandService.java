package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.exception.BrandException;
import com.toyproj.pinchhitterhomerun.entity.Brand;
import com.toyproj.pinchhitterhomerun.repository.BrandRepository;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public ServiceResult<Collection<Brand>> getAllBrand() {
        final var brands = brandRepository.findAll();

        if (brands.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.BRAND_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, brands);
    }

    // 카테고리 아이디로 브랜드 가져오기
    public ServiceResult<Collection<Brand>> getBrandByCategoryId(Long categoryId) {
        final var brands = brandRepository.findByCategoryId(categoryId);

        if (brands.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.BRAND_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, brands);
    }

    // 브랜드 이름으로 브랜드 가져오기
    public ServiceResult<Brand> getBrandByName(String name) {
        final var brand = brandRepository.findByName(name);

        if (brand == null) {
            return new ServiceResult<>(ErrorMessage.BRAND_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, brand);
    }

    // 아이디로 브랜드 가져오기
    public ServiceResult<Brand> getBrandById(Long id) {
        final var brand = brandRepository.findById(id);

        if (brand == null) {
            return new ServiceResult<>(ErrorMessage.BRAND_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, brand);
    }
}
