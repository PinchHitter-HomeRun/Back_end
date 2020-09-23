package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.model.Role;

public interface IRoleRepository {
    Role findByRoleName(String roleName);
    Role findById(Long id);
}
