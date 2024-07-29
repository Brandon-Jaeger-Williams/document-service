package com.assessment.documentservice.mapper;

import com.assessment.documentservice.entity.DocumentEntity;
import com.assessment.documentservice.entity.ProcessedDocumentEntity;
import com.assessment.documentservice.model.DocumentModel;
import com.assessment.documentservice.model.DocumentProcessEvent;
import com.assessment.documentservice.model.ProcessedDocument;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;

public class DocumentMapper {

    public static DocumentEntity mapFrom(String key, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String type = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf(".") + 1);
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setKey(key);
        documentEntity.setFileType(type);
        documentEntity.setFileName(fileName.substring(0, fileName.lastIndexOf(".")));
        return documentEntity;
    }

    public static DocumentProcessEvent mapFrom(UserDetails userDetails, DocumentEntity document) {
        DocumentProcessEvent processEvent = new DocumentProcessEvent();
        processEvent.setInitiatedBy(userDetails.getUsername());
        processEvent.setInitiatedDate(new Date());
        processEvent.setDocumentName(document.getFileName());
        processEvent.setDocumentType(document.getFileType());
        processEvent.setDocumentKey(document.getKey());
        return processEvent;
    }

    public static DocumentModel mapFrom(ProcessedDocumentEntity documentEntity) {
        DocumentModel documentModel = new DocumentModel();
        documentModel.setId(documentEntity.getId());
        documentModel.setCreatedAt(documentEntity.getCreatedAt());
        documentModel.setUpdatedAt(documentEntity.getUpdatedAt());
        documentModel.setFileName(documentEntity.getFileName());
        documentModel.setFileType(documentEntity.getFileType());
        documentModel.setKey(documentEntity.getKey());
        return documentModel;
    }

    public static ProcessedDocumentEntity mapFrom(ProcessedDocument processedDocument, String key) {
        ProcessedDocumentEntity documentEntity = new ProcessedDocumentEntity();
        documentEntity.setFileName(processedDocument.getFileName());
        documentEntity.setFileType(processedDocument.getFileType());
        documentEntity.setKey(key);
        return documentEntity;
    }
}
