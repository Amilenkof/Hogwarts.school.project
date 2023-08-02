package ru.hogwarts.school.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;


import javax.annotation.processing.Generated;
import java.util.Arrays;

@Entity
public class Avatar {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private long fileSize;
    private String mediaType;
//    @Lob
//    @Type("org.")
    private byte[] data;
    @OneToOne
    private Student student;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getFilePath() {
        return filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public Student getStudent() {
        return student;
    }
}
