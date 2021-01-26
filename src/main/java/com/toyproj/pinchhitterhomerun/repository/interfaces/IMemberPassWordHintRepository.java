package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.MemberPasswordHint;

public interface IMemberPassWordHintRepository {
    boolean save(MemberPasswordHint memberPasswordHint);
    MemberPasswordHint findByMemberId(String loginId, String birthDay);
}
