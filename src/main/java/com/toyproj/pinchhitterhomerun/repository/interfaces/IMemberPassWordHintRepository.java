package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.entity.MemberPasswordHint;

public interface IMemberPassWordHintRepository {
    boolean save(MemberPasswordHint memberPasswordHint);
    MemberPasswordHint findById(Long id);
    MemberPasswordHint findByMember(Member member);
}
