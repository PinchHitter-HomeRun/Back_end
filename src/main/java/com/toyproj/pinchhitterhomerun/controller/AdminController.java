package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.request.GrantAdminPermissionReq;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    MemberService memberService;

    @ApiOperation("관리자 권한을 부여하거나 취소")
    @PutMapping
    public ResponseResult<Void> grantPermission(@RequestBody GrantAdminPermissionReq request) {
        final var result = memberService.grantAdminPermission(request.getLoginId(), request.isGrant());

        return new ResponseResult<>(result);
    }
}
