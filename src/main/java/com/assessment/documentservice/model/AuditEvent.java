package com.assessment.documentservice.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class AuditEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = -8429810591575694026L;

    private String initiatedBy;
    private String fromFileName;
    private String fromFileType;
    private String toFileName;
    private String toFileType;
    private String fileDestination;
    private Date timestamp;
}
