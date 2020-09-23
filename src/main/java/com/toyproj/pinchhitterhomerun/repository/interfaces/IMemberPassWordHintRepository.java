package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.model.MemberPasswordHint;

public interface IMemberPassWordHintRepository {
    void save(MemberPasswordHint memberPasswordHint);
    MemberPasswordHint findByMemberId(Long memberId);
}
