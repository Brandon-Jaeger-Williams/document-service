package com.assessment.documentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayOutputStream;


@Data
@AllArgsConstructor
public class ProcessedDocument {

    private String fileName;
    private String fileType;
    private ByteArrayOutputStream stream;
}
