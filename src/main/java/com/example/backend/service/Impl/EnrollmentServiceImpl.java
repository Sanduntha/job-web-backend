package com.example.backend.service.impl;

import com.example.backend.dto.EnrollmentDto;
import com.example.backend.entity.Course;
import com.example.backend.entity.Enrollment;
import com.example.backend.entity.EnrollmentId;
import com.example.backend.entity.JobSeekerProfile;
import com.example.backend.repo.CourseRepo;
import com.example.backend.repo.EnrollmentRepo;
import com.example.backend.repo.JobSeekerProfileRepo;
import com.example.backend.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepo enrollmentRepo;
    private final CourseRepo courseRepo;
    private final JobSeekerProfileRepo jobSeekerProfileRepo;
    private final ModelMapper modelMapper;

    @Override
    public EnrollmentDto enroll(EnrollmentDto dto) {
        EnrollmentId enrollmentId = new EnrollmentId(dto.getJobSeekerId(), dto.getCourseId());

        JobSeekerProfile jobSeeker = jobSeekerProfileRepo.findById(dto.getJobSeekerId())
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));
        Course course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentId);
        enrollment.setJobSeeker(jobSeeker);
        enrollment.setCourse(course);
        enrollment.setDate(dto.getDate());
        enrollment.setAmount(dto.getAmount());

        Enrollment saved = enrollmentRepo.save(enrollment);
        return toDto(saved);
    }

    @Override
    public List<EnrollmentDto> getEnrollmentsByJobSeeker(Long jobSeekerId) {
        return enrollmentRepo.findByJobSeekerId(jobSeekerId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EnrollmentDto toDto(Enrollment e) {
        return new EnrollmentDto(
                e.getId().getCourseId(),
                e.getId().getJobSeekerId(),
                e.getDate(),
                e.getAmount()
        );
    }
}
