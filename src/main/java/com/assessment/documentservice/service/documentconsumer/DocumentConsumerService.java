package com.assessment.documentservice.service.documentconsumer;

import com.assessment.documentservice.model.DocumentProcessEvent;

public interface DocumentConsumerService {

    void process(DocumentProcessEvent processEvent) throws Exception;
}
