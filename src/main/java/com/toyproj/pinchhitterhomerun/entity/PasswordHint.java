package com.toyproj.pinchhitterhomerun.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table (name = "hint_code")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "PasswordHint{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
