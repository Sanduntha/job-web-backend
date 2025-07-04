package com.example.backend.service;

import com.example.backend.dto.TrainerDto;

public interface TrainerService {
    TrainerDto createTrainer(TrainerDto trainerDto) throws Exception;
    TrainerDto getTrainerByUserId(Long userId) throws Exception;
}
