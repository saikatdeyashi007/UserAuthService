package dev.saikat.userauthservice.services;

import dev.saikat.userauthservice.exceptions.PasswordMissmatchException;
import dev.saikat.userauthservice.exceptions.UserAlreadySignedInException;
import dev.saikat.userauthservice.exceptions.UserNotFoundException;
import dev.saikat.userauthservice.models.Role;
import dev.saikat.userauthservice.models.Session;
import dev.saikat.userauthservice.models.State;
import dev.saikat.userauthservice.models.User;
import dev.saikat.userauthservice.pojos.CurrentTime;
import dev.saikat.userauthservice.pojos.UserToken;
import dev.saikat.userauthservice.repositories.RoleRepo;
import dev.saikat.userauthservice.repositories.SessionRepo;
import dev.saikat.userauthservice.repositories.UserRepo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecretKey secretKey;

    CurrentTime currentTime = new CurrentTime();


    //SIGNUP API Logic
    public User signup(String name, String phone, String email, String password, List<Role> rolesFromSignup){
        Optional<User> userOptional= userRepo.findByEmailEquals(email);
        if(userOptional.isPresent()){
            throw new UserAlreadySignedInException("User already signed in, Please login directly");
        }

        User user= new User();
        user.setEmail(email);
        //user.setPassword(password); - High risk of storing password directly in the database
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setName(name);
        user.setPhone(phone);
        user.setCreatedAt(currentTime.getTimeByZone("Asia/Kolkata"));
        user.setLastUpdatedAt(currentTime.getTimeByZone("Asia/Kolkata"));
        user.setState(State.ACTIVE);

        //Now to create the role for the user we need to verify the role name is valid for our database/business/project
        //Lets create an empty list of Role exactly matching the type with User Model
        List<Role> roleForCurrentUser = new LinkedList<>();

        //Now for each role we are getting from sighup request we need to check if that present in our table or not
        for (Role role : rolesFromSignup) {

            //Get the data from Role table in an optional because the role might not exists also
            Optional<Role> rolesFromDb = roleRepo.findByName(role.getName());

            //If value is present in optional then we can add that into our blank list else throw exception
            if (rolesFromDb.isPresent()) {
                roleForCurrentUser.add(rolesFromDb.get());
            }
            else throw new RuntimeException("Role not found: " + role.getName());
        }

        //Save the list for current signup 08user
        user.setRoles(roleForCurrentUser);

        userRepo.save(user);
        return user;
    }


    //LOGIN API Logic

    //public User login(String email, String password){ -- After generating token we have to return token not only the user
    //We can use Pair<User,String> or a Separate Class as POJO (Plain Old Java Object), in our case UserToken is a POJO
    public UserToken login(String email, String password){

        Optional<User> userOptional= userRepo.findByEmailEquals(email);
        //Check if user exists or not
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found, Please sign in first");
        }

        //Get th User object
        User user= userOptional.get();

        //Validate the credentials
        if(bCryptPasswordEncoder.matches(password, user.getPassword())){

            //Logged in successfully
            //JWT Token generating logic

            //Payload generation : we will use Hashmap to store payload as this is a combination of multiple fields
            //We also need time in ms to define the expiry of the token
            Long timeInMs = System.currentTimeMillis();

            HashMap<String, Object> payload= new HashMap<>();
            payload.put("iat", timeInMs);
            payload.put("exp", timeInMs+300000);
            payload.put("userId", user.getId());
            payload.put("iss", "Scaler");

            //Get list of String from List of Roles and then save it to Scope
            List<String> roleNames = new ArrayList<>();
            for (Role role : user.getRoles()) {
                roleNames.add(role.getName());
            }
            payload.put("Scope", roleNames);
            //Advance method to do the above operation: payload.put("Scope", user.getRoles().stream().map(Role::getName).toList());


            //generate JWT
            String token = Jwts.builder().claims(payload).
                    signWith(secretKey).
                    compact();


            //Once the JWT token is generated we need to store that to maintain the source of truth.
            //We will use the Session repo for that

            Session session= new Session();
            session.setToken(token);
            session.setUser(user);
            session.setState(State.ACTIVE);
            session.setCreatedAt(currentTime.getTimeByZone("Asia/Kolkata"));


            List<Session> userSessions = sessionRepo.findActiveSessionsByUserId(user.getId());
            for(Session userSession : userSessions) {
                userSession.setState(State.INACTIVE);
                sessionRepo.save(userSession);
            }

            sessionRepo.save(session);

            return new UserToken(token,user);

        }
        else {
            throw new PasswordMissmatchException("Incorrect Password");
        }
    }
}

/*
Default payload fields:
1. iat = issued at
2. exp = expiry
3. userId = user's ID
4. iss = issued by
5. scope = scope
6. session = session info or session id
 */
