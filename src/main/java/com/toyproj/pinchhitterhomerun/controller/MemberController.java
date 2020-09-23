package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/user/test")
    public String test() {
        return "hi2please";
    }



}
