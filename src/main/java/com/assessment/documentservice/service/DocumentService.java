package com.assessment.documentservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentService {

    String upload(MultipartFile file) throws IOException;

    void process(UserDetails userDetails);
}
