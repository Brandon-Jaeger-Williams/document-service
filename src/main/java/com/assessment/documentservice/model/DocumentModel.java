package com.assessment.documentservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentModel extends AuditModel {

    private String fileName;
    private String fileType;
    private String key;
}
