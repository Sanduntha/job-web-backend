package com.example.backend.controller;

import com.example.backend.dto.ApplicationDto;
import com.example.backend.entity.Application;
import com.example.backend.repo.ApplicationRepo;
import com.example.backend.service.ApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationRepo applicationRepo; // ✅ Add this

    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> applyForJob(
            @RequestPart("application") String applicationJson,
            @RequestPart("cvFile") MultipartFile cvFile
    ) {
        try {
            ApplicationDto applicationDto = new ObjectMapper().readValue(applicationJson, ApplicationDto.class);
            applicationDto.setCvFile(cvFile);
            ApplicationDto saved = applicationService.apply(applicationDto);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Application failed: " + e.getMessage());
        }
    }

    @GetMapping("/jobseeker/{jobSeekerId}")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByJobSeeker(@PathVariable Long jobSeekerId) {
        List<ApplicationDto> list = applicationService.getApplicationsByJobSeeker(jobSeekerId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/download-cv/{applicationId}")
    public ResponseEntity<Resource> downloadCV(@PathVariable Long applicationId) throws Exception {
        Application application = applicationRepo.findById(applicationId)
                .orElseThrow(() -> new Exception("Application not found"));

        File file = new File(application.getCvFilePath());
        if (!file.exists()) {
            throw new Exception("File not found");
        }

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
