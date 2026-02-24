package dev.saikat.userauthservice.pojos;

import dev.saikat.userauthservice.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserToken {

    private String token;
    private User user;

    public UserToken(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
