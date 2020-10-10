package com.toyproj.pinchhitterhomerun.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String name;

    private String address;

    private LocalDateTime createdDate;

    private LocalDateTime deletedDate;

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", brand=" + brand +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", createdDate=" + createdDate +
                ", deletedDate=" + deletedDate +
                '}';
    }
}
