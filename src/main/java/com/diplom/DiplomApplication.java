package com.diplom;

import com.diplom.repo.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiplomApplication {
    @Autowired
    static TestRepo testRepo;


    public static void main(String[] args) {
        SpringApplication.run(DiplomApplication.class, args);



    }


}
