package com.assessment.documentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@Table(name = "audits")
@EqualsAndHashCode(callSuper = true)
public class AuditEventEntity extends AuditEntity {

    @Column(name = "from_file_name")
    @Size(max = 50, message = "The maximum length of fromFileName is 50")
    private String fromFileName;

    @Column(name = "from_file_type")
    @Size(max = 10, message = "The maximum length of fromFileType is 10")
    private String fromFileType;

    @Column(name = "to_file_name")
    @Size(max = 50, message = "The maximum length of toFileName is 50")
    private String toFileName;

    @Column(name = "to_file_type")
    @Size(max = 10, message = "The maximum length of toFileType is 10")
    private String toFileType;

    @Column(name = "file_destination")
    @Size(max = 50, message = "The maximum length of fileDestination is 50")
    private String fileDestination;

    @Column(name = "initiated_by")
    @Size(max = 50, message = "The maximum length of initiatedBy is 50")
    private String initiatedBy;

    @Column(name = "timestamp")
    private Date timestamp;
}
