package com.toyproj.pinchhitterhomerun.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Role {
    @Id @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @OneToMany (mappedBy = "role")
    List<Member> members = new ArrayList<>();

    public Role() {
    }
}
