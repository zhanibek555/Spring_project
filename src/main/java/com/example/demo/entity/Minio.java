package com.example.demo.entity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "minio_status")
public class Minio {

    public Minio() {
    }

    public Minio(String status, String bucketName, String fileName, Instant dateEnd) {
        this.status = status;
        this.bucketName = bucketName;
        this.fileName = fileName;
        this.dateEnd = dateEnd;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "minio_status_id_seq")
    @SequenceGenerator(name = "minio_status_id_seq", sequenceName = "minio_status_id_seq", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(name = "bucket_name", nullable = false)
    private String bucketName;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @CreationTimestamp
    @Column(name = "date_begin", nullable = false, updatable = false)
    private Instant dateBegin;

    @Column(name = "date_end")
    private Instant dateEnd;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Instant getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Instant dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Instant getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Instant dateEnd) {
        this.dateEnd = dateEnd;
    }
}