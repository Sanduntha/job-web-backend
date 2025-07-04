package com.example.backend.controller;

import com.example.backend.dto.TrainerDto;
import com.example.backend.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
@CrossOrigin
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping
    public ResponseEntity<?> createTrainer(@RequestBody TrainerDto trainerDto) {
        try {
            TrainerDto created = trainerService.createTrainer(trainerDto);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTrainerByUserId(@PathVariable Long userId) {
        try {
            TrainerDto trainerDto = trainerService.getTrainerByUserId(userId);
            return ResponseEntity.ok(trainerDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
