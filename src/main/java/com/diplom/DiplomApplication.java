package com.diplom;

import com.diplom.models.User;
import com.diplom.models.UserAuthorities;
import com.diplom.repo.TestRepo;
import com.diplom.repo.UserAuthoritiesRepo;
import com.diplom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DiplomApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomApplication.class, args);
    }
}
