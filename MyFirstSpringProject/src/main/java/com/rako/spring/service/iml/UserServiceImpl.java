package com.rako.spring.service.iml;


import com.rako.spring.exception.UserNotFoundException;
import com.rako.spring.model.User;
import com.rako.spring.repository.UserRepository;
import com.rako.spring.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()){
            return user.get();
        }
        else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User updateUser(User user, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            return userRepository.save(existingUser);
        }
        else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteUserById(long id) {
        if (userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        }
        else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteAllUser() {
        userRepository.deleteAll();
    }


}
