package com.assessment.documentservice.service;

import com.assessment.documentservice.model.DocumentModel;
import com.assessment.documentservice.model.ProcessedDocument;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface ProcessedDocumentService {

    DocumentModel storeDocument(ProcessedDocument processedDocument);

    ByteArrayResource download(Long id) throws IOException;

    Page<DocumentModel> getDocuments(int page, int size);
}
