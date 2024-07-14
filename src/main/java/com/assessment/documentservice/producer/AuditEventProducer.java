package com.assessment.documentservice.producer;

import com.assessment.documentservice.model.AuditEvent;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditEventProducer {

    private final SqsTemplate template;

    public void process(AuditEvent auditEvent) {
        template.send(options -> options
                .queue("audit-queue")
                .payload(auditEvent)
        );
    }
}
