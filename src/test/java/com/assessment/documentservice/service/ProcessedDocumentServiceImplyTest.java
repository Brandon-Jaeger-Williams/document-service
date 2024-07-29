package com.assessment.documentservice.service;

import com.assessment.documentservice.core.exception.NotFoundException;
import com.assessment.documentservice.entity.ProcessedDocumentEntity;
import com.assessment.documentservice.model.DocumentModel;
import com.assessment.documentservice.model.ProcessedDocument;
import com.assessment.documentservice.repository.ProcessedDocumentRepository;
import com.assessment.documentservice.service.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.http.AbortableInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProcessedDocumentServiceImplyTest {

    private ProcessedDocumentService processedDocumentService;

    private final ProcessedDocumentRepository repository = Mockito.mock(ProcessedDocumentRepository.class);

    private final StorageService storageService = Mockito.mock(StorageService.class);

    @BeforeEach
    public void setUp() {
        processedDocumentService = new ProcessedDocumentServiceImpl(repository, storageService);
    }

    @Test
    void test_storeDocument_with_valid_processDocument_should_persist_data() {
        ProcessedDocument processedDocument = new ProcessedDocument("test", "csv", new ByteArrayOutputStream());
        ProcessedDocumentEntity documentEntity = new ProcessedDocumentEntity();
        documentEntity.setId(1L);
        documentEntity.setFileName("test");
        documentEntity.setFileType("pdf");
        documentEntity.setKey("test123");

        when(storageService.uploadDocument(any(), eq("processed"))).thenReturn("test123");
        when(repository.save(any())).thenReturn(documentEntity);

        DocumentModel documentModel = processedDocumentService.storeDocument(processedDocument);

        assertEquals("test", documentModel.getFileName());
        assertEquals("pdf", documentModel.getFileType());
        assertEquals("test123", documentModel.getKey());
    }

    @Test
    void test_download_with_valid_data_should_return_download_data() throws IOException {
        ProcessedDocumentEntity documentEntity = new ProcessedDocumentEntity();
        documentEntity.setId(1L);
        documentEntity.setFileName("test");
        documentEntity.setFileType("pdf");
        documentEntity.setKey("test123");

        ResponseInputStream<GetObjectResponse> res = new ResponseInputStream<>(
                GetObjectResponse.builder().build(),
                AbortableInputStream.create(new ByteArrayInputStream("test".getBytes())));

        when(storageService.getDocument("test123")).thenReturn(res);
        when(repository.findById(any())).thenReturn(Optional.of(documentEntity));

        processedDocumentService.download(1L);

        verify(repository, times(1)).findById(any());
        verify(storageService, times(1)).getDocument(any());
    }

    @Test
    void test_download_with_in_valid_data_should_throw_an_exception() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> processedDocumentService.download(1L));

        assertEquals("Processed document not found with id: 1", exception.getMessage());
    }

    @Test
    void test_getDocuments_should_return_data() {
        ProcessedDocumentEntity documentEntity = new ProcessedDocumentEntity();
        documentEntity.setId(1L);
        documentEntity.setFileName("test");
        documentEntity.setFileType("pdf");
        documentEntity.setKey("test123");

        when(repository.findAllByOrderByUpdatedAtDesc(any())).thenReturn(new PageImpl<>(List.of(documentEntity)));

        Page<DocumentModel> page = processedDocumentService.getDocuments(0, 10);

        assertEquals(1L, page.getTotalElements());
    }
}