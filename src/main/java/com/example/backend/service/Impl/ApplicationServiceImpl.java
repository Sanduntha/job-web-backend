package com.example.backend.service.Impl;

import com.example.backend.dto.ApplicationDto;
import com.example.backend.entity.Application;
import com.example.backend.entity.Job;
import com.example.backend.entity.JobSeekerProfile;
import com.example.backend.repo.ApplicationRepo;
import com.example.backend.repo.JobRepo;
import com.example.backend.repo.JobSeekerProfileRepo;
import com.example.backend.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepo applicationRepo;
    private final JobRepo jobRepo;
    private final JobSeekerProfileRepo jobSeekerRepo;
    private final ModelMapper modelMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ApplicationDto apply(ApplicationDto dto) throws Exception {
        System.out.println("Applying for job ID: " + dto.getJobId() + " by jobseeker ID: " + dto.getJobSeekerId());

        Job job = jobRepo.findById(dto.getJobId())
                .orElseThrow(() -> new Exception("Job not found"));
        JobSeekerProfile js = jobSeekerRepo.findById(dto.getJobSeekerId())
                .orElseThrow(() -> new Exception("JobSeeker not found"));

        Application application = new Application();
        application.setJob(job);
        application.setJobSeeker(js);
        application.setIntroduction(dto.getIntroduction());

        MultipartFile file = dto.getCvFile();
        if (file == null || file.isEmpty()) {
            throw new Exception("CV file is required");
        }

        // Create upload directory if not exist
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) uploadPath.mkdirs();

        // Save with unique filename
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(uploadPath, filename);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new Exception("Could not save file: " + e.getMessage());
        }

        // ✅ Save only relative path like: "uploads/filename.pdf"
        application.setCvFilePath("uploads/" + filename);

        Application saved = applicationRepo.save(application);

        return modelMapper.map(saved, ApplicationDto.class);
    }

    @Override
    public List<ApplicationDto> getApplicationsByJobSeeker(Long jobSeekerId) {
        return applicationRepo.findByJobSeekerId(jobSeekerId).stream()
                .map(app -> modelMapper.map(app, ApplicationDto.class))
                .collect(Collectors.toList());
    }
}
