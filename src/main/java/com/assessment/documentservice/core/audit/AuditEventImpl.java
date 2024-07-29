package com.assessment.documentservice.core.audit;

import com.assessment.documentservice.mapper.AuditEventMapper;
import com.assessment.documentservice.model.DocumentModel;
import com.assessment.documentservice.model.DocumentProcessEvent;
import com.assessment.documentservice.producer.AuditEventProducer;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditEventImpl {

    private final AuditEventProducer auditEventProducer;

    @Around("@annotation(com.assessment.documentservice.core.audit.AuditEvent)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        DocumentProcessEvent processEvent = (DocumentProcessEvent) proceedingJoinPoint.getArgs()[0];
        Object result = proceedingJoinPoint.proceed();

        DocumentModel documentModel = (DocumentModel) result;
        auditEventProducer.process(AuditEventMapper.mapFrom(processEvent, documentModel));
        return result;
    }
}
