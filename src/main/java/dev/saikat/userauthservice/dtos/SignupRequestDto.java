package dev.saikat.userauthservice.dtos;

import dev.saikat.userauthservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignupRequestDto {

    private String name;
    private String phone;
    private String email;
    private String password;
    private List<Role> roles;

}
