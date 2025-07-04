package com.example.backend.service.impl;

import com.example.backend.dto.TrainerDto;
import com.example.backend.entity.TrainerProfile;
import com.example.backend.entity.User;
import com.example.backend.repo.TrainerProfileRepo;
import com.example.backend.repo.UserRepo;
import com.example.backend.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerProfileRepo trainerRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public TrainerDto createTrainer(TrainerDto trainerDto) throws Exception {
        Optional<User> userOpt = userRepo.findById(trainerDto.getUserId());
        if (userOpt.isEmpty()) throw new Exception("User not found");

        TrainerProfile trainer = modelMapper.map(trainerDto, TrainerProfile.class);
        trainer.setUser(userOpt.get());
        TrainerProfile saved = trainerRepo.save(trainer);
        return modelMapper.map(saved, TrainerDto.class);
    }

    @Override
    public TrainerDto getTrainerByUserId(Long userId) throws Exception {
        TrainerProfile trainer = trainerRepo.findByUserId(userId)
                .orElseThrow(() -> new Exception("Trainer not found"));
        return modelMapper.map(trainer, TrainerDto.class);
    }
}
