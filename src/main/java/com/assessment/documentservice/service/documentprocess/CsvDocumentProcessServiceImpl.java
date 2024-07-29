package com.assessment.documentservice.service.documentprocess;

import com.assessment.documentservice.model.DocumentProcessEvent;
import com.assessment.documentservice.model.ProcessedDocument;
import com.assessment.documentservice.service.storage.StorageService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

@Slf4j
@Service("csv")
@RequiredArgsConstructor
public class CsvDocumentProcessServiceImpl implements DocumentProcessService {

    private final StorageService storageService;

    @Override
    public ProcessedDocument process(DocumentProcessEvent processEvent) throws Exception {
        ResponseInputStream<GetObjectResponse> response = storageService.getDocument(processEvent.getDocumentKey());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response));

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        reader.lines().forEach(line -> {
            try {
                document.add(new Paragraph(line));
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        });

        document.close();
        reader.close();

        return new ProcessedDocument(processEvent.getDocumentName(), "pdf", outputStream);
    }
}
