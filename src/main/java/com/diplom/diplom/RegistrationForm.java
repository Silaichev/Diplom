package com.diplom.diplom;

import com.diplom.diplom.models.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private String username;
    private String password;

    public User toUser(){
        return new User(username,password);
    }
}
