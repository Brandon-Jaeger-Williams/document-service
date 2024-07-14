package com.assessment.documentservice.producer;

import com.assessment.documentservice.model.DocumentProcessEvent;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentProducer {

    private final SqsTemplate template;

    public void process(DocumentProcessEvent documentProcessEvent) {
        template.send(options -> options
                .queue("process-document-queue")
                .payload(documentProcessEvent)
        );
    }
}
