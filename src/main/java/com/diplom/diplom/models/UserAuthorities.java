package com.diplom.diplom.models;

import com.diplom.diplom.repo.UserRepo;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserAuthorities {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @OneToMany(mappedBy = "userAuthorities")
    List<User> users;

    String authority;


    public UserAuthorities() {
    }

    public UserAuthorities(User user, String authority) {
        this.users.add(user);
        this.authority = authority;

    }

    public void deleteUserByUser(User user){
        users.remove(user);
    }

    public UserAuthorities(String authority) {
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public  void addUser(User user){
        users.add(user);
    }



    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
