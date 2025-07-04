package com.example.backend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ApplicationDto {

    private Long id;
    private Long jobId;
    private Long jobSeekerId;
    private String introduction;

    // For uploading
    private MultipartFile cvFile;

    // For viewing/downloading
    private String cvFilePath;
}
