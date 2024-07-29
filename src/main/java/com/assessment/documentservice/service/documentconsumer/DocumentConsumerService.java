package com.assessment.documentservice.service.documentconsumer;

import com.assessment.documentservice.model.DocumentModel;
import com.assessment.documentservice.model.DocumentProcessEvent;

public interface DocumentConsumerService {

    DocumentModel process(DocumentProcessEvent processEvent) throws Exception;
}
