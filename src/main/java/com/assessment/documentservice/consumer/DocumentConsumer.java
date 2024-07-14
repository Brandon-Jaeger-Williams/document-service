package com.assessment.documentservice.consumer;

import com.assessment.documentservice.model.DocumentProcessEvent;
import com.assessment.documentservice.service.documentconsumer.DocumentConsumerService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentConsumer {

    private final DocumentConsumerService documentConsumerService;

    @SqsListener("process-document-queue")
    public void listen(DocumentProcessEvent data) throws Exception {
        log.info(String.format("Consuming document event for " +
                "fileName: %s, initiated by %s", data.getDocumentName(), data.getInitiatedBy()));
        documentConsumerService.process(data);
    }
}
