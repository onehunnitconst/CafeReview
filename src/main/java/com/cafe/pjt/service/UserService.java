package com.cafe.pjt.service;

import com.cafe.pjt.repository.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void join(User user);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    boolean matchPassword(String rawPassword, String encodedPassword);
}
