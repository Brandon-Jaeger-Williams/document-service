package com.assessment.documentservice.service.documentconsumer;

import com.assessment.documentservice.core.exception.BusinessException;
import com.assessment.documentservice.model.DocumentModel;
import com.assessment.documentservice.model.DocumentProcessEvent;
import com.assessment.documentservice.model.ProcessedDocument;
import com.assessment.documentservice.service.ProcessedDocumentService;
import com.assessment.documentservice.service.documentprocess.CsvDocumentProcessServiceImpl;
import com.assessment.documentservice.service.documentprocess.DocumentProcessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DocumentConsumerServiceImplTest {

    private DocumentConsumerService documentConsumerService;

    private final DocumentProcessService documentProcessService = Mockito.mock(CsvDocumentProcessServiceImpl.class);

    private final Map<String, DocumentProcessService> documentProcessServiceMap = new HashMap<>();

    private final ProcessedDocumentService processedDocumentService = Mockito.mock(ProcessedDocumentService.class);

    @BeforeEach
    public void setUp() {
        documentProcessServiceMap.put("csv", documentProcessService);
        documentConsumerService = new DocumentConsumerServiceImpl(documentProcessServiceMap, processedDocumentService);
    }

    @Test
    void test_process_with_csv_file_should_call_valid_process() throws Exception {
        DocumentProcessEvent processEvent = new DocumentProcessEvent();
        processEvent.setDocumentType("csv");
        processEvent.setDocumentKey("test123");
        processEvent.setDocumentName("test");

        ProcessedDocument processedDocument = new ProcessedDocument("test", "csv", new ByteArrayOutputStream());

        when(documentProcessService.process(processEvent)).thenReturn(processedDocument);
        when(processedDocumentService.storeDocument(processedDocument)).thenReturn(new DocumentModel());

        documentConsumerService.process(processEvent);

        verify(documentProcessService, times(1)).process(processEvent);
        verify(processedDocumentService, times(1)).storeDocument(processedDocument);
    }

    @Test
    void test_process_with_pdf_file_should_throw_exception() {
        DocumentProcessEvent processEvent = new DocumentProcessEvent();
        processEvent.setDocumentType("pdf");
        processEvent.setDocumentKey("test123");
        processEvent.setDocumentName("test");

        Exception exception = assertThrows(BusinessException.class, () -> documentConsumerService.process(processEvent));

        assertEquals("Not able to process document type: pdf", exception.getMessage());
    }
}