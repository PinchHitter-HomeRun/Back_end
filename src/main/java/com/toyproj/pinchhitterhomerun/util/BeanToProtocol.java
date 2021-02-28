package com.toyproj.pinchhitterhomerun.util;

import com.toyproj.pinchhitterhomerun.bean.BoardContentResultBean;
import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.response.BoardContentRes;
import com.toyproj.pinchhitterhomerun.response.MemberRes;
import com.toyproj.pinchhitterhomerun.type.MatchType;

public class BeanToProtocol {

    public static void copyPropertyMemberRes(Member member, MemberRes memberRes) {
        memberRes.setId(member.getId());
        memberRes.setName(member.getName());
        memberRes.setBranch(member.getBranch());
        memberRes.setRole(member.getRole());
    }

    public static void copyPropertyBoardContentRes(BoardContentResultBean contentResultBean, BoardContentRes contentRes) {
        contentRes.setId(contentResultBean.getId());
        contentRes.setAuthor(contentResultBean.getAuthor());
        contentRes.setContent(contentResultBean.getContent());
        contentRes.setPayType(contentResultBean.getPayType().getValue());
        contentRes.setPay(contentResultBean.getPay());
        contentRes.setStarDate(contentResultBean.getStarDate());
        contentRes.setEndDate(contentResultBean.getEndDate());
        contentRes.setWriteData(contentResultBean.getWriteDate());
        contentRes.setMatchType(MatchType.Waiting.getValue());
    }
}
