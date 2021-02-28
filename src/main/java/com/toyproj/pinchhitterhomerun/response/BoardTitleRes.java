package com.toyproj.pinchhitterhomerun.response;

import com.toyproj.pinchhitterhomerun.bean.BoardTitleResultBean;
import com.toyproj.pinchhitterhomerun.bean.MemberBoardBean;
import com.toyproj.pinchhitterhomerun.type.MatchType;
import com.toyproj.pinchhitterhomerun.type.PayType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Setter
public class BoardTitleRes {
    private Collection<BoardTitleResultBean> boardTitleBeans;
}
