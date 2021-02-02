package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.Brand;

import java.util.Collection;
import java.util.List;

public interface IBrandRepository {
    Collection<Brand> findAll();
    Collection<Brand> findByCategoryId(Long categoryId);
    Brand findByName(String name);
    Brand findById(Long id);
}
