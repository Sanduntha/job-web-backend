package com.example.backend.repo;

import com.example.backend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepo extends JpaRepository<Application, Long> {
    List<Application> findByJobSeekerId(Long jobSeekerId);

    List<Application> findByJobId(Long jobId);
}
