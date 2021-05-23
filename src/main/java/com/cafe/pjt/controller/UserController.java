package com.cafe.pjt.controller;

import com.cafe.pjt.dto.UserDTO;
import com.cafe.pjt.service.UserService;
import com.cafe.pjt.vo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        Optional<User> findUser = userService.findById(id);
        if (findUser.isPresent()) {
            User foundUser = findUser.get();
            return ResponseEntity.ok(UserDTO.builder()
                                            .email(foundUser.getEmail())
                                            .nickname(foundUser.getNickname())
                                            .build());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value={"", "/"})
    public ResponseEntity<?> getUsers() {
        List<User> findUsers = userService.findAll();
        return ResponseEntity.ok(findUsers.stream().map(user -> UserDTO.builder().email(user.getEmail()).nickname(user.getNickname()).build()));
    }
}
