package com.cafe.pjt.controller;

import com.cafe.pjt.repository.model.User;
import com.cafe.pjt.service.UserService;
import com.cafe.pjt.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

/**
 * Spring Security에 내장되어있는 BCryptPasswordEncoder를 테스트하기 위한 Contoller입니다.  <br />
 *
 * @author 백상수 (walewalu)
 */

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    /**
     * 사용자 정보를 POST 방식의 HTTP Request의 Body로 받습니다. <br />
     * 성공하면 200 OK를 응답하고 Store에 저장하며, 중복 시 400 Bad Request를 보냅니다.
     *
     * @param user HTTP Request의 body에서 받은 user form
     * @return {@link ResponseEntity} – HTTP Response
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody User user) {

        // 아이디 중복 체크
        Optional<User> findUser = userService.findByUsername(user.getUsername());
        if (findUser.isPresent())
            return ResponseEntity.badRequest().body("register failed: username is already existed");

        // 확인 후 저장
        userService.join(user);

        return ResponseEntity.ok("succesfully registered");
    }

    /**
     * 사용자 정보를 POST 방식의 HTTP Request의 Body로 받습니다. <br />
     * 성공하면 200 OK를 응답하고 로그인 성공 메시지를 출력합니다. <br />
     * 아이디를 찾을 수 없을 경우 404 Not Found를 응답합니다. <br />
     * 비밀번호가 일치하지 않을 경우 400 Bad Request를 응답합니다.
     *
     * @param user HTTP Request의 body에서 받은 user form
     * @return {@link ResponseEntity} – HTTP Response
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody User user) {

        // 아이디 존재 여부 체크
        Optional<User> findUser = userService.findByUsername(user.getUsername());

        if (findUser.isPresent()) {
            // 존재 시 비밀번호 비교, 일치할 시 200, 아닐 시 401 상태 코드 응답
            User foundUser = findUser.get();
            return userService.matchPassword(user.getPassword(), foundUser.getPassword()) ?
                    ResponseEntity.ok("login success: hello " + foundUser.getUsername()) :
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("login failed: incorrect password");
        }

        // 존재하지 않을 시 404 상태 코드 응답
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("login failed: username is not found");
    }
}
