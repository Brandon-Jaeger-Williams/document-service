package com.assessment.documentservice.service.documentconsumer;

import com.assessment.documentservice.core.audit.AuditEvent;
import com.assessment.documentservice.core.exception.BusinessException;
import com.assessment.documentservice.core.timer.Timer;
import com.assessment.documentservice.model.DocumentModel;
import com.assessment.documentservice.model.DocumentProcessEvent;
import com.assessment.documentservice.model.ProcessedDocument;
import com.assessment.documentservice.service.ProcessedDocumentService;
import com.assessment.documentservice.service.documentprocess.DocumentProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentConsumerServiceImpl implements DocumentConsumerService {

    private final Map<String, DocumentProcessService> documentProcessServiceMap;
    private final ProcessedDocumentService processedDocumentService;

    @Timer
    @AuditEvent
    @Override
    public DocumentModel process(DocumentProcessEvent processEvent) throws Exception {
        DocumentProcessService documentProcessService = documentProcessServiceMap.get(processEvent.getDocumentType());

        if (documentProcessService == null) {
            throw new BusinessException(String.format("Not able to process document type: %s", processEvent.getDocumentType()));
        }

        ProcessedDocument processedDocument = documentProcessService.process(processEvent);
        return processedDocumentService.storeDocument(processedDocument);
    }
}
