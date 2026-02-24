package dev.saikat.userauthservice.configurations;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;


@Configuration
public class AuthConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //To disable API authorization
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.cors().disable();
        httpSecurity.csrf().disable();
        httpSecurity.authorizeHttpRequests(autorize-> autorize.anyRequest().permitAll());
        return httpSecurity.build();
    }

    //Process 1 :
    @Bean
    public SecretKey secretKey() {
        //To generate the header we need to define the algorithm, Spring security has inbuilt features called MacAlgorithm
        MacAlgorithm algorithm= Jwts.SIG.HS256;
        SecretKey secret= algorithm.key().build();
        return secret;
    }


    //Process 2 :

//    @Value("${secret.key}")
//    private String secret;
//    @Bean
//    public SecretKey secretKey() {
//        return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//    }

}
