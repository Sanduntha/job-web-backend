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
    public JobSeekerDto createJobSeeker(JobSeekerDto dto) throws Exception {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new Exception("User not found"));

        JobSeekerProfile profile = new JobSeekerProfile();
        profile.setName(dto.getName());
        profile.setJobCategory(dto.getJobCategory());
        profile.setSkill(dto.getSkill());
        profile.setContactNumber(dto.getContactNumber());
        profile.setUser(user);

        JobSeekerProfile saved = jobSeekerRepo.save(profile);

        JobSeekerDto response = new JobSeekerDto();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setJobCategory(saved.getJobCategory());
        response.setSkill(saved.getSkill());
        response.setContactNumber(saved.getContactNumber());
        response.setUserId(saved.getUser().getId());

        return response;
    }

    @Override
    public JobSeekerDto getByUserId(Long userId) throws Exception {
        JobSeekerProfile js = jobSeekerRepo.findByUserId(userId)
                .orElseThrow(() -> new Exception("JobSeeker not found"));
        return modelMapper.map(js, JobSeekerDto.class);
    }
}
