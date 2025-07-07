package com.example.backend.service.Impl;

import com.example.backend.dto.EmployerDto;
import com.example.backend.entity.EmployerProfile;
import com.example.backend.entity.User;
import com.example.backend.repo.EmployerProfileRepo;
import com.example.backend.repo.UserRepo;
import com.example.backend.service.EmployerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {

    private final EmployerProfileRepo employerRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public EmployerDto createEmployer(EmployerDto employerDto) throws Exception {
        Optional<User> userOpt = userRepo.findById(employerDto.getUserId());
        if (userOpt.isEmpty()) throw new Exception("User not found");

        EmployerProfile employer = modelMapper.map(employerDto, EmployerProfile.class);
        employer.setUser(userOpt.get());
        EmployerProfile saved = employerRepo.save(employer);
        return modelMapper.map(saved, EmployerDto.class);
    }

    @Override
    public EmployerDto getEmployerByUserId(Long userId) throws Exception {
        EmployerProfile emp = employerRepo.findByUserId(userId)
                .orElseThrow(() -> new Exception("Employer not found"));
        return modelMapper.map(emp, EmployerDto.class);
    }

    @Override
    public EmployerDto getEmployerByEmail(String email) {
        return null;
    }

    @PostConstruct
    public void init() {
        modelMapper.typeMap(EmployerDto.class, EmployerProfile.class).addMappings(mapper ->
                mapper.skip(EmployerProfile::setUser)
        );
    }

}
