package com.diplom.repo;

import com.diplom.models.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;



public interface UserRepo extends CrudRepository<User,Long> {

    User findByUsername(String name);

    @Transactional
    void deleteByUsername(String username);

    @Transactional
    void deleteById(Long id);

}
