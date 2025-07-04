package com.example.backend.service.Impl;

import com.example.backend.dto.LoginData;
import com.example.backend.dto.UserDto;
import com.example.backend.entity.User;
import com.example.backend.repo.UserRepo;
import com.example.backend.service.UserService;
import com.example.backend.util.JWTTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final JWTTokenGenerator tokenGenerator;
    private final ModelMapper modelMapper = new ModelMapper();

    // Base64 encode
    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    // Base64 decode
    private String decodePassword(String encodedPassword) {
        return new String(Base64.getDecoder().decode(encodedPassword));
    }

    @Override
    public UserDto register(UserDto userDto) throws Exception {
        if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
            throw new Exception("Email already registered");
        }

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(encodePassword(userDto.getPassword())); // encode base64

        User savedUser = userRepo.save(user);

        UserDto responseDto = modelMapper.map(savedUser, UserDto.class);
        responseDto.setPassword(null);  // hide password in response
        return responseDto;
    }

    @Override
    public LoginData login(LoginData loginRequest) throws Exception {
        Optional<User> userOpt = userRepo.findByEmail(loginRequest.getEmail());

        if (userOpt.isEmpty()) {
            throw new Exception("User not found");
        }

        User user = userOpt.get();

        String decodedPassword = decodePassword(user.getPassword());
        if (!decodedPassword.equals(loginRequest.getPassword())) {  // fix here
            throw new Exception("Invalid credentials");
        }

        String token = tokenGenerator.generateToken(user.getEmail());

        LoginData response = new LoginData();
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setToken(token);
        return response;
    }


}
