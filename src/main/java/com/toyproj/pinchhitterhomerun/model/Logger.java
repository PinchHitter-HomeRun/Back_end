package com.toyproj.pinchhitterhomerun.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Logger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String request;

    private String url;

    private LocalDateTime createdDate;

    public Logger() {
    }

    public Logger(String request, String url) {
        this.request = request;
        this.url = url;
        this.createdDate = LocalDateTime.now();
    }
}
