package com.assessment.documentservice.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    String uploadDocument(MultipartFile file) throws IOException;

    byte[] getDocument(String key) throws IOException;
}
