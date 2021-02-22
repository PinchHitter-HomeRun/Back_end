package com.toyproj.pinchhitterhomerun.util;

import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.response.MemberRes;

public class EntityToProtocol {

    public static void toMemberRes(Member member, MemberRes memberRes) {
        memberRes.setId(member.getId());
        memberRes.setName(member.getName());
        memberRes.setBranch(member.getBranch());
        memberRes.setRole(member.getRole());
    }
}
