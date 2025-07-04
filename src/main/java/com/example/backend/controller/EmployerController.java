package com.example.backend.controller;

import com.example.backend.dto.EmployerDto;
import com.example.backend.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employers")
@RequiredArgsConstructor
@CrossOrigin
public class EmployerController {

    private final EmployerService employerService;

    @PostMapping
    public ResponseEntity<?> createEmployer(@RequestBody EmployerDto employerDto) {
        try {
            EmployerDto created = employerService.createEmployer(employerDto);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getEmployerByUserId(@PathVariable Long userId) {
        try {
            EmployerDto employerDto = employerService.getEmployerByUserId(userId);
            return ResponseEntity.ok(employerDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
