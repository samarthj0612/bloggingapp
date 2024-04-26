package com.samdb.bloggingapp.services.impl;

import com.samdb.bloggingapp.entities.User;
import com.samdb.bloggingapp.exceptions.ResourceNotFound;
import com.samdb.bloggingapp.payloads.UserDto;
import com.samdb.bloggingapp.repositories.UserRepo;
import com.samdb.bloggingapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.userDtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        UserDto savedUserDto;
        savedUserDto = this.userToUserDto(savedUser);

        return savedUserDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "Id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(user);
        UserDto updatedUserDto;
        updatedUserDto = this.userToUserDto(updatedUser);

        return updatedUserDto;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "Id", userId));
        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();

        List<UserDto> userDtos;
        userDtos = users.stream().map(user -> this.userToUserDto(user)).collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public UserDto deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "Id", userId));
        this.userRepo.delete(user);
        UserDto userDto;
        userDto = this.userToUserDto(user);

        return userDto;
    }

    private User userDtoToUser(UserDto userDto) {
        // User user = new User();
        // user.setId(userDto.getId());
        // user.setName(userDto.getName());
        // user.setEmail(userDto.getEmail());
        // user.setAbout(userDto.getAbout());
        // user.setPassword(userDto.getPassword());

        User user;
        user = this.modelMapper.map(userDto, User.class);

        return user;
    }

    private UserDto userToUserDto(User user) {
        // UserDto userDto = new UserDto();
        // userDto.setId(user.getId());
        // userDto.setName(user.getName());
        // userDto.setEmail(user.getEmail());
        // userDto.setPassword(user.getPassword());
        // userDto.setAbout(user.getAbout());

        UserDto userDto;
        userDto = this.modelMapper.map(user, UserDto.class);

        return userDto;
    }
}
