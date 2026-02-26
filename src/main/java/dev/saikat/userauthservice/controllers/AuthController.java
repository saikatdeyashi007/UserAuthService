package dev.saikat.userauthservice.controllers;

import dev.saikat.userauthservice.dtos.LoginRequestDto;
import dev.saikat.userauthservice.dtos.SignupRequestDto;
import dev.saikat.userauthservice.dtos.UserDto;
import dev.saikat.userauthservice.models.User;
import dev.saikat.userauthservice.pojos.UserToken;
import dev.saikat.userauthservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto){

        //We need to return the token in Header


        try{
            UserToken userToken = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.COOKIE,userToken.getToken());
            HttpHeaders httpHeaders = new HttpHeaders(headers);


            return new ResponseEntity<>(UserDto.convertToUserDto(userToken.getUser()),
                    httpHeaders,
                    HttpStatus.OK);


        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto){
        try{
            User user= authService.signup(signupRequestDto.getName(),
                    signupRequestDto.getPhone(),
                    signupRequestDto.getEmail(),
                    signupRequestDto.getPassword(),
                    signupRequestDto.getRoles());
            return UserDto.convertToUserDto(user);
        }
        catch (Exception exception){
            throw exception;
        }
    }
}
