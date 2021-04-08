package com.diplom.diplom.security;

import com.diplom.diplom.models.User;
import com.diplom.diplom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {
        User user = userRepo.findByUsername(name);
        if(user!=null){
            return user;
        }
        throw new UsernameNotFoundException("User "+name+" not found");
    }


}*/
