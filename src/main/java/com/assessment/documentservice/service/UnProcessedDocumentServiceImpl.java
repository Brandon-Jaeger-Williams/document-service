package com.assessment.documentservice.service;

import com.assessment.documentservice.core.timer.Timer;
import com.assessment.documentservice.entity.DocumentEntity;
import com.assessment.documentservice.mapper.DocumentMapper;
import com.assessment.documentservice.producer.DocumentProducer;
import com.assessment.documentservice.repository.DocumentRepository;
import com.assessment.documentservice.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnProcessedDocumentServiceImpl implements UnProcessedDocumentService {

    private final DocumentRepository documentRepository;

    private final StorageService storageService;

    private final DocumentProducer documentProducer;

    @Timer
    @Override
    public String upload(MultipartFile file) throws IOException {
        String key = storageService.uploadDocument(file.getBytes());
        DocumentEntity documentEntity = DocumentMapper.mapFrom(key, file);
        documentRepository.save(documentEntity);
        return key;
    }

    @Override
    public void process(UserDetails userDetails) {
        documentRepository.findByProcessedFalse().forEach(document -> {
            try {
                documentProducer.process(DocumentMapper.mapFrom(userDetails, document));
                document.setProcessed(true);
                documentRepository.save(document);
            } catch (Exception e) {
                log.error(String.format("An error occurred trying to process unprocessed files: %s", document.getFileName()), e);
            }
        });
    }
}
