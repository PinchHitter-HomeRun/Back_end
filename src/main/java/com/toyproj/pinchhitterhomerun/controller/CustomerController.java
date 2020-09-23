package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {


    @PostMapping("/requestId")
    public boolean checkDuplicate(@PathVariable String requestId) {
        return false;
    }
}
