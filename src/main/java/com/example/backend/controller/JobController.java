package com.example.backend.controller;

import com.example.backend.dto.JobDto;
import com.example.backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@CrossOrigin
public class JobController {

    private final JobService jobService;

    @PostMapping("/add")
    public ResponseEntity<?> addJob(@RequestBody JobDto jobDto) {
        try {
            JobDto saved = jobService.addJob(jobDto);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/update/{jobId}")
    public ResponseEntity<?> updateJob(@PathVariable Long jobId, @RequestBody JobDto jobDto) {
        try {
            jobDto.setId(jobId); // ✅ Set the ID from the URL path
            JobDto updated = jobService.updateJob(jobDto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<?> deleteJob(@PathVariable Long jobId) {
        try {
            jobService.deleteJob(jobId);
            return ResponseEntity.ok("Job deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<JobDto>> getJobsByEmployer(@PathVariable Long employerId) {
        List<JobDto> jobs = jobService.getJobsByEmployer(employerId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobDto>> searchJobs(@RequestParam String keyword) {
        List<JobDto> jobs = jobService.searchJobs(keyword);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping
    public ResponseEntity<List<JobDto>> getAllJobs() {
        List<JobDto> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

}
