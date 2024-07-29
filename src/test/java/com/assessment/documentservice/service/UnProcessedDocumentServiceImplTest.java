package com.assessment.documentservice.service;

import com.assessment.documentservice.entity.DocumentEntity;
import com.assessment.documentservice.entity.UserEntity;
import com.assessment.documentservice.producer.DocumentProducer;
import com.assessment.documentservice.repository.DocumentRepository;
import com.assessment.documentservice.service.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UnProcessedDocumentServiceImplTest {

    private UnProcessedDocumentService documentService;

    private final DocumentRepository documentRepository = Mockito.mock(DocumentRepository.class);

    private final StorageService storageService = Mockito.mock(StorageService.class);

    private final DocumentProducer documentProducer = Mockito.mock(DocumentProducer.class);

    @BeforeEach
    public void setUp() {
        documentService = new UnProcessedDocumentServiceImpl(documentRepository, storageService, documentProducer);
    }

    @Test
    void test_uploadDocument_with_file_should_return_key() throws IOException {
        MultipartFile file = new MockMultipartFile(
                "file",
                "hello.csv",
                "text/csv",
                "Hello, World!".getBytes()
        );

        when(storageService.uploadDocument(any())).thenReturn("testKey");
        when(documentRepository.save(any())).thenReturn(any());

        String key = documentService.upload(file);

        assertEquals("testKey", key);
    }

    @Test
    void test_process_with_pending_documents_should_send_to_document_process_queue() {
        DocumentEntity documentEntity1 = new DocumentEntity();
        documentEntity1.setFileName("test1");
        documentEntity1.setKey("test1");
        documentEntity1.setFileType("csv");

        DocumentEntity documentEntity2 = new DocumentEntity();
        documentEntity2.setFileName("test2");
        documentEntity2.setKey("test2");
        documentEntity2.setFileType("csv");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Test123");

        when(documentRepository.findByProcessedFalse()).thenReturn(List.of(documentEntity1, documentEntity2));
        when(documentRepository.save(any())).thenReturn(documentEntity1);
        doNothing().when(documentProducer).process(any());

        documentService.process(userEntity);

        verify(documentRepository, times(2)).save(any());
        verify(documentProducer, times(2)).process(any());
    }

}