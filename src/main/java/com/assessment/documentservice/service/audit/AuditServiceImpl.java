package com.assessment.documentservice.service.audit;

import com.assessment.documentservice.mapper.AuditEventMapper;
import com.assessment.documentservice.model.AuditEvent;
import com.assessment.documentservice.repository.AuditEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditEventRepository auditEventRepository;

    @Override
    public void create(AuditEvent auditEvent) {
        auditEventRepository.save(AuditEventMapper.mapFrom(auditEvent));
    }
}
