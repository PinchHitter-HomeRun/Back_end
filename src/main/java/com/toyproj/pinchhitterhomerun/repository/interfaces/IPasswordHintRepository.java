package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.PasswordHint;

import java.util.Collection;
import java.util.List;

public interface IPasswordHintRepository {
    PasswordHint findById(Long id);
    Collection<PasswordHint> findAll();
}
