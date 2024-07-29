package com.assessment.documentservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class AuditModel {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
}
