package com.assessment.documentservice.repository;

import com.assessment.documentservice.entity.AuditEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditEventRepository extends JpaRepository<AuditEventEntity, Long> {
}
