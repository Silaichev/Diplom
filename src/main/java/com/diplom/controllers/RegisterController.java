package com.diplom.controllers;

import com.diplom.RegistrationForm;
import com.diplom.models.User;
import com.diplom.models.UserAuthorities;
import com.diplom.repo.UserAuthoritiesRepo;
import com.diplom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {


    private UserRepo userRepo;
    @Autowired
    private UserAuthoritiesRepo userAuthoritiesRepo;
    private PasswordEncoder passwordEncoder;

    public RegisterController(UserRepo userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder= passwordEncoder;

    }
    @GetMapping
    public String registerForm(){
        return"registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form){
        User out = form.toUser();
        userRepo.save(out);
        User in = userRepo.findByUsername(out.getUsername());
        UserAuthorities authorities = userAuthoritiesRepo.
                findUserAuthoritiesByAuthority("ROLE_USER");

        in.setUserAuthorities(authorities);
        authorities.addUser(in);
        userAuthoritiesRepo.save(authorities);
        userRepo.save(in);
        return"redirect:/login";
    }
    @PostConstruct
    private void saveAuthorities(){
        User admin = new User("Admin","q2w1e4r3");
        userRepo.save(admin);
        admin = userRepo.findByUsername(admin.getUsername());
        UserAuthorities authorities = new UserAuthorities(admin, "ROLE_ADMIN");
        admin.setUserAuthorities(authorities);
        authorities.addUser(admin);
        userAuthoritiesRepo.save(authorities);
        userRepo.save(admin);

        User user = new User("q2w1e4r3","q2w1e4r3");
        userRepo.save(user);
        user = userRepo.findByUsername(user.getUsername());
        UserAuthorities authorities2 = new UserAuthorities(user, "ROLE_USER");
        user.setUserAuthorities(authorities2);
        authorities2.addUser(user);
        userAuthoritiesRepo.save(authorities2);
        userRepo.save(user);

    }
    /*private void saveAuthorities(){
        User admin = new User("Admin","q2w1e4r3");
        userRepo.save(admin);
        admin = userRepo.findByUsername(admin.getUsername());
        UserAuthorities authorities = new UserAuthorities(admin, "ROLE_ADMIN");
        admin.setUserAuthorities(authorities);
        authorities.addUser(admin);
        userAuthoritiesRepo.save(authorities);
        userRepo.save(admin);

        User user = new User("q2w1e4r3","q2w1e4r3");
        userRepo.save(user);
        user = userRepo.findByUsername(user.getUsername());
        UserAuthorities authorities2 = new UserAuthorities(user, "ROLE_USER");
        user.setUserAuthorities(authorities2);
        authorities2.addUser(user);
        userAuthoritiesRepo.save(authorities2);
        userRepo.save(user);

    }
*/
}
