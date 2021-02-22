package com.toyproj.pinchhitterhomerun.response;

import com.toyproj.pinchhitterhomerun.entity.Branch;
import com.toyproj.pinchhitterhomerun.entity.Role;
import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class MemberRes {
    private Long id;
    private String name;
    private Branch branch;
    private Role role;
}
