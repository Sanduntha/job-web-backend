package com.example.backend.service.impl;

import com.example.backend.dto.JobSeekerDto;
import com.example.backend.entity.JobSeekerProfile;
import com.example.backend.entity.User;
import com.example.backend.repo.JobSeekerProfileRepo;
import com.example.backend.repo.UserRepo;
import com.example.backend.service.JobSeekerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobSeekerServiceImpl implements JobSeekerService {

    private final JobSeekerProfileRepo jobSeekerRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public JobSeekerDto createJobSeeker(JobSeekerDto jobSeekerDto) throws Exception {
        Optional<User> userOpt = userRepo.findById(jobSeekerDto.getUserId());
        if (userOpt.isEmpty()) throw new Exception("User not found");

        JobSeekerProfile jobSeeker = modelMapper.map(jobSeekerDto, JobSeekerProfile.class);
        jobSeeker.setUser(userOpt.get());
        JobSeekerProfile saved = jobSeekerRepo.save(jobSeeker);
        return modelMapper.map(saved, JobSeekerDto.class);
    }

    @Override
    public JobSeekerDto getByUserId(Long userId) throws Exception {
        JobSeekerProfile js = jobSeekerRepo.findByUserId(userId)
                .orElseThrow(() -> new Exception("JobSeeker not found"));
        return modelMapper.map(js, JobSeekerDto.class);
    }
}
