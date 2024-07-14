package com.assessment.documentservice.service;

import com.assessment.documentservice.repository.DocumentRepository;
import com.assessment.documentservice.service.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DocumentServiceImplTest {

    private DocumentService documentService;

    private final DocumentRepository documentRepository = Mockito.mock(DocumentRepository.class);

    private final StorageService storageService = Mockito.mock(StorageService.class);

    @BeforeEach
    public void setUp() {
        documentService = new DocumentServiceImpl(documentRepository, storageService);
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

}