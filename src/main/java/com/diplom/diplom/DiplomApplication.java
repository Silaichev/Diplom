package com.diplom.diplom;

import com.diplom.diplom.models.User;
import com.diplom.diplom.models.UserAuthorities;
import com.diplom.diplom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

@SpringBootApplication
public class DiplomApplication {
    @Autowired
    static UserRepo userRepo;


    public static void main(String[] args) {
        SpringApplication.run(DiplomApplication.class, args);



    }


}
