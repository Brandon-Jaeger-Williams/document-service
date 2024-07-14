package com.assessment.documentservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentService {

    String upload(MultipartFile file) throws IOException;

}
