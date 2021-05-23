package com.cafe.pjt.repository;

import com.cafe.pjt.dto.UserDTO;
import com.cafe.pjt.dto.UserLoginRequestDTO;
import com.cafe.pjt.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    void save (User user);
    List<User> findById(Long id);
    List<User> findByEmail(String email);
    List<User> findAll();
    void updateUser(User user);
}
