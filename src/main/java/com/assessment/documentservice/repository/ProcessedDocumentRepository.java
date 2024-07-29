package com.assessment.documentservice.repository;

import com.assessment.documentservice.entity.ProcessedDocumentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedDocumentRepository extends JpaRepository<ProcessedDocumentEntity, Long> {

    Page<ProcessedDocumentEntity> findAllByOrderByUpdatedAtDesc(Pageable pageable);
}
