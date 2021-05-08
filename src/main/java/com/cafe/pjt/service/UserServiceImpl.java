package com.cafe.pjt.service;

import com.cafe.pjt.repository.UserMapper;
import com.cafe.pjt.vo.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void join(User user) {
        userMapper.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userMapper.findByEmail(email).stream().findFirst();
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
