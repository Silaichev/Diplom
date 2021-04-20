package com.diplom.diplom.controllers;

import com.diplom.diplom.models.User;
import com.diplom.diplom.models.UserAuthorities;
import com.diplom.diplom.repo.UserAuthoritiesRepo;
import com.diplom.diplom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserAuthoritiesRepo userAuthoritiesRepo;

    @GetMapping("/deleteUserByUsername/{username}")
    public void deleteUserById(@PathVariable String username){
        UserAuthorities ua = userAuthoritiesRepo.findUserAuthoritiesByAuthority("ROLE_USER");
        ua.deleteUserByUser(userRepo.findByUsername(username));
        userRepo.deleteByUsername(username);
    }

}
