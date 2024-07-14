package com.assessment.documentservice.controller;

import com.assessment.documentservice.core.security.CurrentUser;
import com.assessment.documentservice.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/documents")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public String uploadDocument(@RequestParam("file") MultipartFile file) throws IOException {
        return documentService.upload(file);
    }

    @PostMapping("/process")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void process(@CurrentUser UserDetails userDetails) {
        documentService.process(userDetails);
    }
}
