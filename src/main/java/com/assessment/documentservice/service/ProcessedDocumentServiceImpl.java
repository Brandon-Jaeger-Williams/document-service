package com.assessment.documentservice.service;

import com.assessment.documentservice.core.exception.NotFoundException;
import com.assessment.documentservice.entity.ProcessedDocumentEntity;
import com.assessment.documentservice.mapper.DocumentMapper;
import com.assessment.documentservice.model.DocumentModel;
import com.assessment.documentservice.model.ProcessedDocument;
import com.assessment.documentservice.repository.ProcessedDocumentRepository;
import com.assessment.documentservice.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessedDocumentServiceImpl implements ProcessedDocumentService {

    private final ProcessedDocumentRepository repository;

    private final StorageService storageService;

    @Override
    public DocumentModel storeDocument(ProcessedDocument processedDocument) {
        String key = storageService.uploadDocument(processedDocument.getStream().toByteArray());
        ProcessedDocumentEntity documentEntity = DocumentMapper.mapFrom(processedDocument, key);
        return DocumentMapper.mapFrom(repository.save(documentEntity));
    }

    @Override
    public ByteArrayResource download(Long id) throws IOException {
        ProcessedDocumentEntity document = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Processed document not found with id: " + id)
        );

        ResponseInputStream<GetObjectResponse> response = storageService.getDocument(document.getKey());
        return new ByteArrayResource(response.readAllBytes());

    }

    @Override
    public Page<DocumentModel> getDocuments(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProcessedDocumentEntity> documents = repository.findAllByOrderByUpdatedAtDesc(pageRequest);
        return documents.map(DocumentMapper::mapFrom);
    }
}
