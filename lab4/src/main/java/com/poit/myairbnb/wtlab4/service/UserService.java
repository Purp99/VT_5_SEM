package com.poit.myairbnb.wtlab4.service;

import com.poit.myairbnb.wtlab4.entity.User;
import com.poit.myairbnb.wtlab4.repository.UserRepository;
import java.sql.SQLException;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public void register(User user)
    {
        try {
            userRepository.save(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User login(String email, String password) {
        try {
            User user = userRepository.findByEmail(email);
            if (user.getPassword().equals(password)) {
                return user;
            }
        } catch (SQLException ignored) {
            System.err.println("ew");
        }

        return null;
    }
}
