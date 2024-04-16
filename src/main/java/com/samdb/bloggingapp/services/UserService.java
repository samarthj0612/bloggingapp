package com.samdb.bloggingapp.services;

import com.samdb.bloggingapp.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    UserDto deleteUser(Integer userId);

}
