package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.PasswordHint;

import java.util.List;

public interface IPasswordHintRepository {
    PasswordHint findById(Long id);
    PasswordHint findByText(String text);
    List<PasswordHint> findAll();
}
