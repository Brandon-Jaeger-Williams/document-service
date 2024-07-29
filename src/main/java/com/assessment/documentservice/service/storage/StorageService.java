package com.assessment.documentservice.service.storage;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

public interface StorageService {

    String uploadDocument(byte[] bytes, String path);

    ResponseInputStream<GetObjectResponse> getDocument(String key);
}
