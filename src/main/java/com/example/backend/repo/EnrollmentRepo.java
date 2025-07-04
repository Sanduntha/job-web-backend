package com.example.backend.repo;

import com.example.backend.entity.Enrollment;
import com.example.backend.entity.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepo extends JpaRepository<Enrollment, EnrollmentId> {
    List<Enrollment> findByJobSeekerId(Long jobSeekerId);
}
