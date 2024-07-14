package com.assessment.documentservice.service.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    @Value("${aws.bucket}")
    private String bucket;

    private final S3Client s3Client;

    @Override
    public String uploadDocument(MultipartFile file) throws IOException {
        String key = UUID.randomUUID().toString();
        byte[] bytes = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, bytes.length));
        return key;
    }

    @Override
    public byte[] getDocument(String key) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);
        return response.readAllBytes();
    }
}
