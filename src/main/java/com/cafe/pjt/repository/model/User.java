package com.cafe.pjt.repository.model;

import lombok.*;

/**
 * 테스트를 위한 User 도메인입니다.
 *
 * @author 백상수
 */

@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    private String username;
    private String password;

    @Override
    public String toString() {
        return "username: " + username + ", password: " + password;
    }
}
