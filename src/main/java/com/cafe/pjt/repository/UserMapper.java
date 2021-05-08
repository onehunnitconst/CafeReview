package com.cafe.pjt.repository;

import com.cafe.pjt.dto.UserLoginRequestDTO;
import com.cafe.pjt.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
    void save (User user);
    List<User> findByEmail(String email);
}
