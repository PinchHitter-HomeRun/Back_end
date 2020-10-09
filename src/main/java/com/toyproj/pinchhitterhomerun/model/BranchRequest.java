package com.toyproj.pinchhitterhomerun.model;

import com.toyproj.pinchhitterhomerun.type.AcceptType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BranchRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer memberId;

    private Integer branchId;

    @Enumerated(EnumType.STRING)
    private AcceptType isAccept;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime deletedDate;
}
