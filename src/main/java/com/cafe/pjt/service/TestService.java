package com.cafe.pjt.service;

import com.cafe.pjt.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Spring Security에 내장되어있는 BCryptPasswordEncoder를 테스트하기 위한 Serivce입니다. <br />
 * 이 클래스가 사용하는 bcrypt 암호 해시 방식은 아래 위키백과 링크를 참조하면 도움이 됩니다. <br />
 * 테스트용이므로 BCryptPasswordEncoder는 임의로 이 클래스에서 필드로 선언하고 객체를 생성합니다. <br />
 * 같은 이유로 데이터는 별도의 데이터베이스 없이 mockRepos로 메모리에 저장하고 조회합니다.
 *
 * @author 백상수 (walewalu)
 * @see <a href=https://ko.wikipedia.org/wiki/Bcrypt>위키백과 Bcrypt</a>
 */

@Service
public class TestService {

    /**
     * 테스트용이므로 BCryptPasswordEncoder는 임의로 이 클래스에서 필드로 선언하고 객체를 생성합니다. <br />
     * 같은 이유로 데이터는 별도의 데이터베이스 없이 mockRepos로 메모리에 저장하고 조회합니다.
     */
    private final Map<Long, User> mockRepos = new HashMap<>();
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private Long id = 1L;

    /**
     * user 데이터를 mockRepos에 저장합니다.
     *
     * @param user
     */
    public void join(User user) {
        mockRepos.put(id++, user);
        System.out.println("mockRepos = " + mockRepos);
    }

    /**
     * username으로 사용자를 찾아 반환합니다.
     * Optional에 실어 반환하여 null 관리를 합니다.
     *
     * @param username 유저 이름
     * @return user (Optional)
     */
    public Optional<User> findUserByUsername(String username) {
        User findUser = mockRepos.entrySet().stream()
                .filter(map -> username.equals(map.getValue().getUsername()))
                .findFirst().get().getValue();
        return Optional.ofNullable(findUser);
    }

    /**
     * 사용자의 비밀번호를 암호화합니다.
     *
     * @param password 유저 비밀번호
     * @return bcrypt 방식으로 암호화된 비밀번호
     */
    public String encodePassword (String password) {
        return encoder.encode(password);
    }

    /**
     * 제출받은 비밀번호를 암호화하여 사용자의 비밀번호와 비교합니다.
     *
     * @param submittedPassword 제출받은 비밀번호
     * @param encodedUserPassword 사용자의 비밀번호
     * @return 일치 여부를 boolean으로 반환
     */
    public boolean matchPassword (String submittedPassword, String encodedUserPassword) {
        return encoder.matches(submittedPassword, encodedUserPassword);
    }

}
