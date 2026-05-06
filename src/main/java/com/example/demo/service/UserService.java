package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    static List<User> userList =new ArrayList<>();
    public UserService(){
//        users.add(new User(1,"Rahul"));
//        users.add(new User(2,"Amit"));
    }


    static {
        userList.add(new User(1,"Rahul"));
        userList.add(new User(2,"Amit"));
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public User getUserById(int id) {
        return userList.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addUser(User user){
        userList.add(user);
    }
}
