package com.toyproj.pinchhitterhomerun.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table (name = "hint_code")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class PasswordHint {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public PasswordHint() {
    }
}
