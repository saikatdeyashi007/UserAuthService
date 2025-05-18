package dev.saikat.userauthservice.controllers;

import dev.saikat.userauthservice.dtos.LoginRequestDto;
import dev.saikat.userauthservice.dtos.SignupRequestDto;
import dev.saikat.userauthservice.dtos.UserDto;
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
        return new UserDto();
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto){
        return new UserDto();
    }

}
