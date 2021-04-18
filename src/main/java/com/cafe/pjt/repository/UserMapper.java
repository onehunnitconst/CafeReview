package com.cafe.pjt.repository;

import com.cafe.pjt.repository.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
    void save (User user);
    List<User> findByUsername(String username);
}
