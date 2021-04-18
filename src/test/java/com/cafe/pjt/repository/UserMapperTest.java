package com.cafe.pjt.repository;

import com.cafe.pjt.repository.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Rollback
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    public void before() {
        User entity = new User("sangstein", "1234");
        userMapper.save(entity);
    }

    @Test
    public void test() {
        List<User> users = userMapper.findByUsername("sangstein");
        users.forEach(user -> {
            System.out.println(user);
            assertThat(user.getUsername()).isNotNull();
        });
    }
}