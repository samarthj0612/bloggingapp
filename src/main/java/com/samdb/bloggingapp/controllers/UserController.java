package com.samdb.bloggingapp.controllers;

import com.samdb.bloggingapp.payloads.UserDto;
import com.samdb.bloggingapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{Id}")
    public ResponseEntity<?> getUserById(@PathVariable String Id){
        // try{
            Integer userId = Integer.parseInt(Id);
            UserDto user = this.userService.getUserById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        // } catch (Exception e){
            // return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        // }
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/user/{Id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String Id) {
        try{
            Integer userId = Integer.parseInt(Id);
            userDto = this.userService.updateUser(userDto, userId);

            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/user/{Id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable String Id) {
        try{
            Integer userId = Integer.parseInt(Id);
            UserDto userDto = this.userService.deleteUser(userId);

            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
