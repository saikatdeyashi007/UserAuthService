package dev.saikat.userauthservice.services;

import dev.saikat.userauthservice.exceptions.PasswordMissmatchException;
import dev.saikat.userauthservice.exceptions.UserAlreadySignedInException;
import dev.saikat.userauthservice.exceptions.UserNotFoundException;
import dev.saikat.userauthservice.models.User;
import dev.saikat.userauthservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;


    public User signup(String name, String phone, String email, String password){
        Optional<User> userOptional= userRepo.findByEmailEquals(email);
        if(userOptional.isPresent()){
            throw new UserAlreadySignedInException("User already signed in, Please login directly");
        }

        User user= new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        userRepo.save(user);
        return user;
    }

    public User login(String email, String password){

        Optional<User> userOptional= userRepo.findByEmailEquals(email);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found, Please sign in first");
        }
        String pwd= userOptional.get().getPassword();
        if(!pwd.equals(password)){
            throw new PasswordMissmatchException("Incorrect Password");
        }
        return userOptional.get();
    }

}
