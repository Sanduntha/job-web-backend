package com.example.backend.controller;

import com.example.backend.dto.EnrollmentDto;
import com.example.backend.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@CrossOrigin
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<?> enrollCourse(@RequestBody EnrollmentDto enrollmentDto) {
        try {
            EnrollmentDto enrolled = enrollmentService.enroll(enrollmentDto);
            return ResponseEntity.ok(enrolled);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/jobseeker/{jobSeekerId}")
    public ResponseEntity<List<EnrollmentDto>> getEnrollmentsByJobSeeker(@PathVariable Long jobSeekerId) {
        List<EnrollmentDto> enrollments = enrollmentService.getEnrollmentsByJobSeeker(jobSeekerId);
        return ResponseEntity.ok(enrollments);
    }
}
