package com.toyproj.pinchhitterhomerun.model;

import com.toyproj.pinchhitterhomerun.type.AcceptType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "branch_request")
public class BranchRequest extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long branchId;

    @Enumerated(EnumType.STRING)
    private AcceptType isAccept;

//    private LocalDateTime createdDate;
//
//    private LocalDateTime updatedDate;
//
//    private LocalDateTime deletedDate;

    public BranchRequest(Long memberId, Long branchId) {
        this.memberId = memberId;
        this.branchId = branchId;
    }

    public void setIsAccept(AcceptType acceptType) {
        this.isAccept = acceptType;
        this.setUpdatedDate();
    }

    public void softDelete() {
        this.setDeletedDate();
    }

    @Override
    public String toString() {
        return "BranchRequest{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", branchId=" + branchId +
                ", isAccept=" + isAccept +
                ", createdDate=" + this.getCreatedDate() +
                ", updatedDate=" + this.getUpdatedDate() +
                ", deletedDate=" + this.getDeletedDate() +
                '}';
    }
}
