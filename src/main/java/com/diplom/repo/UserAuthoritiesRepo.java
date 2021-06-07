package com.diplom.repo;

import com.diplom.models.UserAuthorities;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserAuthoritiesRepo extends CrudRepository<UserAuthorities,Long> {

    UserAuthorities findUserAuthoritiesByAuthority(String authority);

    UserAuthorities findByAuthority(String authority);

    List<UserAuthorities> findAll();

    UserAuthorities findUserAuthoritiesById(Long id);
}
