package com.assessment.documentservice.service.documentprocess;

import com.assessment.documentservice.model.DocumentProcessEvent;
import com.assessment.documentservice.model.ProcessedDocument;

public interface DocumentProcessService {

    ProcessedDocument process(DocumentProcessEvent processEvent) throws Exception;
}
