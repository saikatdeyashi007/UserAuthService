package dev.saikat.userauthservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    private String name;
    private String phone;
    private String email;
    private String password;

}
