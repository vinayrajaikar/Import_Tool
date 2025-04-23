package com.project.import_tool.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "ImportData")
public class ImportData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "IMPORT_STATUS", nullable = false)
    private String importStatus;

    @Column(name = "ENTITY", nullable = false)
    private String entity;

    @Column(name = "INPUT_CSV_FILE")
    @Lob
    private String inputCsvFile;

    @Column(name = "FAILED_RECORDS")
    @Lob
    private String failedRecords;

    @Column(name = "CREATED_AT")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getInputCsvFile() {
        return inputCsvFile;
    }

    public void setInputCsvFile(String inputCsvFile) {
        this.inputCsvFile = inputCsvFile;
    }

    public String getFailedRecords() {
        return failedRecords;
    }

    public void setFailedRecords(String failedRecords) {
        this.failedRecords = failedRecords;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
