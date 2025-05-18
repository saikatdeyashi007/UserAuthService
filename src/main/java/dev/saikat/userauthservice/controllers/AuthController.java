package dev.saikat.userauthservice.controllers;

import dev.saikat.userauthservice.dtos.LoginRequestDto;
import dev.saikat.userauthservice.dtos.SignupRequestDto;
import dev.saikat.userauthservice.dtos.UserDto;
import dev.saikat.userauthservice.models.User;
import dev.saikat.userauthservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private  AuthService authService;

    @PostMapping("/login")
    public UserDto login(@RequestBody LoginRequestDto loginRequestDto){
        try{
            User user= authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            return convertToUserDto(user);
        }
        catch (Exception exception){
            throw exception;
        }
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto){
        try{
            User user= authService.signup(signupRequestDto.getName(), signupRequestDto.getPhone(), signupRequestDto.getEmail(), signupRequestDto.getPassword());
            return convertToUserDto(user);
        }
        catch (Exception exception){
            throw exception;
        }
    }

    private UserDto convertToUserDto(User user){
        UserDto userDto= new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
