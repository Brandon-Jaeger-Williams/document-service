package com.assessment.documentservice.mapper;

import com.assessment.documentservice.entity.AuditEventEntity;
import com.assessment.documentservice.model.AuditEvent;
import com.assessment.documentservice.model.DocumentModel;
import com.assessment.documentservice.model.DocumentProcessEvent;

public class AuditEventMapper {

    public static AuditEvent mapFrom(DocumentProcessEvent processEvent, DocumentModel documentModel) {
        AuditEvent auditEvent = new AuditEvent();
        auditEvent.setInitiatedBy(processEvent.getInitiatedBy());
        auditEvent.setTimestamp(processEvent.getInitiatedDate());
        auditEvent.setFromFileName(processEvent.getDocumentName());
        auditEvent.setFromFileType(processEvent.getDocumentType());
        auditEvent.setToFileName(documentModel.getFileName());
        auditEvent.setToFileType(documentModel.getFileType());
        auditEvent.setFileDestination("test");
        return auditEvent;
    }

    public static AuditEventEntity mapFrom(AuditEvent auditEvent) {
        AuditEventEntity auditEventEntity = new AuditEventEntity();
        auditEventEntity.setInitiatedBy(auditEvent.getInitiatedBy());
        auditEventEntity.setFromFileName(auditEvent.getFromFileName());
        auditEventEntity.setFromFileType(auditEvent.getFromFileType());
        auditEventEntity.setToFileName(auditEvent.getToFileName());
        auditEventEntity.setToFileType(auditEvent.getToFileType());
        auditEventEntity.setFileDestination(auditEvent.getFileDestination());
        auditEventEntity.setTimestamp(auditEvent.getTimestamp());
        return auditEventEntity;
    }
}
