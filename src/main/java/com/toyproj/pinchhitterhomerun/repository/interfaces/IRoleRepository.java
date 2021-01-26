package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.Role;

public interface IRoleRepository {
    Role findByRoleName(String roleName);
    Role findById(Long id);
}
