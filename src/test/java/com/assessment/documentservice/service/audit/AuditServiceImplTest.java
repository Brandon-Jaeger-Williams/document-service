package com.assessment.documentservice.service.audit;

import com.assessment.documentservice.entity.AuditEventEntity;
import com.assessment.documentservice.model.AuditEvent;
import com.assessment.documentservice.repository.AuditEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuditServiceImplTest {

    private AuditService auditService;

    private final AuditEventRepository auditEventRepository = Mockito.mock(AuditEventRepository.class);

    @BeforeEach
    public void setUp() {
        auditService = new AuditServiceImpl(auditEventRepository);
    }

    @Test
    void test_create_with_valid_object_should_persist_object() {
        AuditEventEntity auditEntity = new AuditEventEntity();
        auditEntity.setId(1L);

        AuditEvent auditEvent = new AuditEvent();

        when(auditEventRepository.save(any())).thenReturn(auditEntity);

        auditService.create(auditEvent);

        verify(auditEventRepository, times(1)).save(any());
    }
}