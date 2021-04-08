package com.diplom.diplom.repo;

import com.diplom.diplom.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;


public interface UserRepo extends CrudRepository<User,Long> {

    User findByUsername(String name);

    @Transactional
    void deleteByUsername(String username);
}
