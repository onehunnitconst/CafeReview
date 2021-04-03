package com.cafe.pjt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security의 설정 파일입니다. <br />
 *
 * @author 백상수 (walewalu)
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Spring Security 사용 시 Config 메서드입니다.
     * 허가되지 않은 사용자에 대한 API 요청을 풀기 위하여 http.authorizeRequests().anyRequest().authenticated()를 비활성화합니다.
     * Spring Security에 내장된 BCrypt 암호화를 테스트하기 위함입니다.
     *
     * @param http HttpSecurity 객체로, 각종 보안 설정에 필요합니다.
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .configurationSource(corsConfigurationSource())
        .and()
                .csrf().disable()
//                .authorizeRequests()
//                    .anyRequest().authenticated()
//        .and()
                .httpBasic();
    }

    /**
     * Spring Security 사용 시 CORS에 대한 설정입니다. <br />
     * 추후 프론트엔드와의 통신을 위한 설정입니다.
     *
     * @return {@link CorsConfigurationSource}
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }



}
