package com.toyproj.pinchhitterhomerun.entity;

import com.toyproj.pinchhitterhomerun.util.TimeManager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.ZoneId;

@MappedSuperclass
@Getter
@ToString
public class Base {

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime deletedDate;

//    protected void setCreatedDate() {
//        this.createdDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
//    }

    protected void setUpdatedDate() {
        this.updatedDate = TimeManager.now();
    }

    protected void setDeletedDate() {
        this.deletedDate = TimeManager.now();
    }
}
