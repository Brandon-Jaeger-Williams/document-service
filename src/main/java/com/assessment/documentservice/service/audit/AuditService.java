package com.assessment.documentservice.service.audit;

import com.assessment.documentservice.model.AuditEvent;

public interface AuditService {

    void create(AuditEvent auditEvent);
}
