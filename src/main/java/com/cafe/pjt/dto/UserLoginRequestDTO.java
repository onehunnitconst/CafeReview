package com.cafe.pjt.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 테스트를 위한 User 도메인입니다.
 *
 * @author 백상수
 */

@Data
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserLoginRequestDTO {

    @Email(message = "아이디는 이메일 형식이어야 합니다.")
    @NotBlank(message = "아이디를 입력해주세요.")
    String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password;
}
