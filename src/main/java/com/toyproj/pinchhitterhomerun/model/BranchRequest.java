package com.toyproj.pinchhitterhomerun.model;

import com.toyproj.pinchhitterhomerun.type.AcceptType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor
public class BranchRequest extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer memberId;

    private Integer branchId;

    @Enumerated(EnumType.STRING)
    private AcceptType isAccept;

//    private LocalDateTime createdDate;
//
//    private LocalDateTime updatedDate;
//
//    private LocalDateTime deletedDate;

    public void setIsAccept(AcceptType acceptType) {
        this.isAccept = acceptType;
        this.setUpdatedDate(LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime());
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
