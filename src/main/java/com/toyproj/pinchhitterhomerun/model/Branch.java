package com.toyproj.pinchhitterhomerun.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Branch extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

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
