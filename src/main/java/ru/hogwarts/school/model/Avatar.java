package ru.hogwarts.school.model;

import jakarta.persistence.*;
import nonapi.io.github.classgraph.json.Id;

import javax.annotation.processing.Generated;
@Entity
public class Avatar {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] data;
    @OneToOne
    private Student student;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
