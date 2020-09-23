package com.toyproj.pinchhitterhomerun.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany (mappedBy = "role", fetch = FetchType.LAZY)
    List<Member> members = new ArrayList<>();

    public Role() {
    }
}
