package com.toyproj.pinchhitterhomerun.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Branch extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String name;

    private String address;

//    private LocalDateTime createdDate;
//
//    private LocalDateTime deletedDate;


    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", brand=" + brand +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", createdDate=" + this.getCreatedDate() +
                ", deletedDate=" + this.getDeletedDate() +
                '}';
    }
}
