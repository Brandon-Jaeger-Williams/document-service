package com.assessment.documentservice.consumer;

import com.assessment.documentservice.model.AuditEvent;
import com.assessment.documentservice.service.audit.AuditService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditEventConsumer {

    private final AuditService auditService;

    @SqsListener("audit-queue")
    public void listen(AuditEvent data) {
        log.info(String.format("Consuming audit event for " +
                "fileName: %s, initiated by %s", data.getFromFileName(), data.getInitiatedBy()));
        auditService.create(data);
    }
}
