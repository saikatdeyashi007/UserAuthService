package dev.saikat.userauthservice.dtos;

import dev.saikat.userauthservice.models.Role;
import dev.saikat.userauthservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String phone;
    private String email;
    private List<Role> roles;


    public static UserDto convertToUserDto(User user){
        UserDto userDto= new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

}
