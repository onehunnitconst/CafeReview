package com.cafe.pjt.service;

import com.cafe.pjt.vo.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void join(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String username);
    List<User> findAll();
    String encodePassword(String password);
    boolean matchPassword(String rawPassword, String encodedPassword);
}
