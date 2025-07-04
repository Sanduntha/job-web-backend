package com.example.backend.service;

import com.example.backend.dto.LoginData;
import com.example.backend.dto.UserDto;

public interface UserService {

    UserDto register(UserDto userDto) throws Exception;
    LoginData login(LoginData loginData) throws Exception;
}
