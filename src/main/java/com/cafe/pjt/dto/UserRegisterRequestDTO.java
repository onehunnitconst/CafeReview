package com.cafe.pjt.dto;

import com.cafe.pjt.vo.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRegisterRequestDTO {

    @Email(message = "아이디는 이메일 형식이어야 합니다.")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String email;

    @Length(min = 10, max = 32, message = "비밀번호는 10자 이상 32자 이하로 설정해주세요.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{0,}",
            message = "비밀번호는 알파벳 대문자, 소문자, 숫자, 특수문자를 하나씩 포함해야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordConfirm;

    @Length(max = 8, message = "닉네임은 8자 이내로 설정해주세요.")
    @NotBlank(message = "닉네임은 비워둘 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "닉네임에 특수문자는 사용할 수 없습니다.")
    private String nickname;


    public boolean isMatchPasswordConfirm(){
        return password.equals(passwordConfirm);
    }

    public User buildToValueObject() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
