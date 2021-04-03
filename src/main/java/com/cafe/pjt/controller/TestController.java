package com.cafe.pjt.controller;

import com.cafe.pjt.domain.User;
import com.cafe.pjt.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Spring Security에 내장되어있는 BCryptPasswordEncoder를 테스트하기 위한 Contoller입니다.  <br />
 *
 * @author 백상수 (walewalu)
 * @see TestService
 */

@Controller
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello";
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
        Optional<User> findUser = testService.findUserByUsername(user.getUsername());
        if (findUser.isPresent())
            return ResponseEntity.badRequest().body("register failed: username is already existed");

        // 비밀번호 암호화 후 저장
        String encodedPassword = testService.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);
        testService.join(user);

        return ResponseEntity.ok("succesfully registered");
    }

    /**
     * 사용자 정보를 POST 방식의 HTTP Request의 Body로 받습니다. <br />
     * 성공하면 200 OK를 응답하고 로그인 성공 메시지를 출력합니다. <br />
     * 아이디를 찾을 수 없을 경우 404 Not Found를 응답합니다. <br />
     * 비밀번호가 일치하지 않을 경우 401 Unauthorized를 응답합니다.
     *
     * @param user HTTP Request의 body에서 받은 user form
     * @return {@link ResponseEntity} – HTTP Response
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody User user) {

        // 아이디 존재 여부 체크
        Optional<User> findUser = testService.findUserByUsername(user.getUsername());

        if (findUser.isPresent()) {
            // 존재 시 비밀번호 비교, 일치할 시 200, 아닐 시 401 상태 코드 응답
            User foundUser = findUser.get();
            return testService.matchPassword(user.getPassword(), foundUser.getPassword()) ?
                    ResponseEntity.ok("login success: hello " + foundUser.getPassword()) :
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login failed: incorrect password");
        }

        // 존재하지 않을 시 404 상태 코드 응답
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("login failed: username is not found");
    }
}
