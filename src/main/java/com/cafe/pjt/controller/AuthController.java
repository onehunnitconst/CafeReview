package com.cafe.pjt.controller;

import com.cafe.pjt.dto.UserLoginRequestDTO;
import com.cafe.pjt.dto.UserRegisterRequestDTO;
import com.cafe.pjt.service.UserService;
import com.cafe.pjt.service.UserServiceImpl;
import com.cafe.pjt.session.UserSession;
import com.cafe.pjt.vo.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

/**
 * <br />
 *
 * @author 백상수 (walewalu)
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserSession userSession;
    private final UserService userService;

    public AuthController(UserSession userSession, UserServiceImpl userServiceImpl) {
        this.userSession = userSession;
        this.userService = userServiceImpl;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequestDTO userRegisterRequest) {

        // 아이디 중복 체크
        Optional<User> findUser = userService.findByEmail(userRegisterRequest.getEmail());
        if (findUser.isPresent()) {
            return ResponseEntity.badRequest().body("이미 가입한 아이디입니다.");
        }

        // 비밀번호 - 비밀번호 확인 일치하는지 체크
        if (!userRegisterRequest.isMatchPasswordConfirm()) {
            return ResponseEntity.badRequest().body("비밀번호 확인이 틀립니다.");
        }

        // 확인 후 저장
        String encodedPassword = userService.encodePassword(userRegisterRequest.getPassword());
        userRegisterRequest.setPassword(encodedPassword);
        userService.join(userRegisterRequest.buildToValueObject());

        return ResponseEntity.ok("회원가입에 성공하였습니다.");
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequest) {

        // 아이디 존재 여부 체크
        Optional<User> findUser = userService.findByEmail(userLoginRequest.getEmail());

        if (findUser.isPresent()) {
            // 존재 시 비밀번호 비교, 일치할 시 200, 아닐 시 401 상태 코드 응답
            User foundUser = findUser.get();
            if ( !userService.matchPassword(userLoginRequest.getPassword(), foundUser.getPassword()) ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
            }
            userSession.setId(foundUser.getId());
            userSession.setEmail(foundUser.getEmail());
            userSession.setNickname(foundUser.getNickname());
            return ResponseEntity.ok("로그인에 성공하였습니다.");
        }

        // 존재하지 않을 시 404 상태 코드 응답
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보를 찾을 수 없습니다.");
    }

    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<?> check() {
        return ResponseEntity.ok(userSession.toString());
    }

    @GetMapping("/logout")
    @ResponseBody
    public ResponseEntity<?> logout(HttpSession session) {
        try {
            session.invalidate();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("로그아웃에 성공하였습니다.");
    }
}
