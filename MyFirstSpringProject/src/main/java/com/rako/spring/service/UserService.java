package com.rako.spring.service;

import com.rako.spring.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> findAll();

    User getUserById(long id);

    User getUserByUsername(String username);

    User updateUser(User user, Long id);

    void deleteUserById(long id);

    void deleteAllUser();

}