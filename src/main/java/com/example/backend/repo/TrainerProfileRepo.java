package com.example.backend.repo;

import com.example.backend.entity.TrainerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerProfileRepo extends JpaRepository<TrainerProfile, Long> {
    Optional<TrainerProfile> findByUserId(Long userId);
}
