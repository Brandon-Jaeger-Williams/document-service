package com.assessment.documentservice.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class DocumentProcessEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = -8872421123275130876L;

    private String initiatedBy;
    private Date initiatedDate;
    private String documentName;
    private String documentType;
    private String documentKey;
}
