package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.MemberPasswordHint;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.responsebean.MemberHintAns;
import com.toyproj.pinchhitterhomerun.service.MemberPasswordHintService;
import com.toyproj.pinchhitterhomerun.service.PasswordHintService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/hints")
public class PasswordHintController {

    @Autowired
    PasswordHintService passwordHintService;

    @ApiOperation("설정 가능한 모든 질문 리스트")
    @GetMapping
    public ResponseResult<Collection<String>> getHintList() {
        final var result = passwordHintService.getAllHint();

        return new ResponseResult<>(result);
    }
}
