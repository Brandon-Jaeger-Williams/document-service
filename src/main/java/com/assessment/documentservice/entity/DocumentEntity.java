package com.assessment.documentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.io.Serial;

@Entity
@Data
@Table(name = "documents")
@EqualsAndHashCode(callSuper = true)
public class DocumentEntity extends AuditEntity {

    @Serial
    private static final long serialVersionUID = 5131034819195176496L;

    @Column(name = "file_name")
    @Size(max = 50, message = "The maximum length of fileName is 50")
    private String fileName;

    @Column(name = "file_type")
    @Size(max = 50, message = "The maximum length of fileType is 50")
    private String fileType;

    @Column(name = "key")
    private String key;

    @Column(name = "processed")
    private Boolean processed;

    @PrePersist
    protected void onCreate() {
        processed = false;
    }
}
