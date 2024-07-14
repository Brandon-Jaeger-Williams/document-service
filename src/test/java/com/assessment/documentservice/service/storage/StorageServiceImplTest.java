package com.assessment.documentservice.service.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StorageServiceImplTest {

    private StorageService storageService;

    private final S3Client s3Client = Mockito.mock(S3Client.class);

    @BeforeEach
    public void setUp() {
        storageService = new StorageServiceImpl(s3Client);
    }

    @Test
    void test_uploadDocument_with_file_should_return_key() throws IOException {
        MultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                "text/csv",
                "Hello, World!".getBytes()
        );

        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenReturn(null);

        String key = storageService.uploadDocument(file);

        assertNotNull(key);
    }
}