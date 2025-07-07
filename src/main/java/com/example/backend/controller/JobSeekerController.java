package com.example.backend.controller;

import com.example.backend.dto.JobSeekerDto;
import com.example.backend.service.JobSeekerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobseekers")
@RequiredArgsConstructor
@CrossOrigin
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;

    @PostMapping
    public ResponseEntity<?> createJobSeeker(@RequestBody JobSeekerDto jobSeekerDto) {
        try {
            JobSeekerDto created = jobSeekerService.createJobSeeker(jobSeekerDto);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getJobSeekerByUserId(@PathVariable Long userId) {
        try {
            JobSeekerDto js = jobSeekerService.getByUserId(userId);
            return ResponseEntity.ok(js);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Job Seeker profile not found");
        }

    }
}
