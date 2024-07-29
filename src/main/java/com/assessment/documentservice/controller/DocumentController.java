package com.assessment.documentservice.controller;

import com.assessment.documentservice.core.security.CurrentUser;
import com.assessment.documentservice.model.DocumentModel;
import com.assessment.documentservice.service.ProcessedDocumentService;
import com.assessment.documentservice.service.UnProcessedDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/documents")
public class DocumentController {

    private final UnProcessedDocumentService unProcessedDocumentService;
    private final ProcessedDocumentService processedDocumentService;

    @PostMapping
    public String uploadDocument(@RequestParam("file") MultipartFile file) throws IOException {
        return unProcessedDocumentService.upload(file);
    }

    @PostMapping("/process")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void process(@CurrentUser UserDetails userDetails) {
        unProcessedDocumentService.process(userDetails);
    }

    @GetMapping
    public Page<DocumentModel> getDocuments(@RequestParam int page, @RequestParam int size) {
        return processedDocumentService.getDocuments(page, size);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(processedDocumentService.download(id));
    }
}
