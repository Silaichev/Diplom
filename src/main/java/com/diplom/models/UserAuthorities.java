package com.diplom.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        Object o = Objects.isNull(users) || users.isEmpty() ? users = new ArrayList<>(Arrays.asList(user)) : this.users.add(user);
        this.authority = authority;

    }
    public UserAuthorities(List<User> incomeUsers, String authority) {
        Object o = Objects.isNull(users) || users.isEmpty() ? users = incomeUsers : this.users.addAll(incomeUsers);
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
        Object o = Objects.isNull(users) || users.isEmpty() ? users = new ArrayList<>(Arrays.asList(user)) : users.add(user);
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
