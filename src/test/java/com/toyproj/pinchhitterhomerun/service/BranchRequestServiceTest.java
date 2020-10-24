package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.repository.BranchRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BranchRequestServiceTest {
    @Autowired
    BranchRequestRepository branchRequestRepository;
    @Autowired
    BranchRequestService branchRequestService;
}