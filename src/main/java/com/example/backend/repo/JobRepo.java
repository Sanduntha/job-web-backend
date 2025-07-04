package com.example.backend.repo;

import com.example.backend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepo extends JpaRepository<Job, Long> {
    List<Job> findByEmployerId(Long employerId);

    List<Job> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
}
