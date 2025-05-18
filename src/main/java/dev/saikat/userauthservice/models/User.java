package dev.saikat.userauthservice.models;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseModel{

    private String name;
    private String phone;
    private String email;
    private String password;

}
