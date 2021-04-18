package com.cafe.pjt.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.cafe.pjt.repository.model.User;
import com.cafe.pjt.service.UserServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Rollback
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserServiceImpl testService;

    @Test
    @Transactional
    @Rollback
    public void registerTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("username", "aaa");
        body.put("password", "bbb");

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        String url = "http://localhost:" + port + "/register";
        assertThat(this.restTemplate.postForObject(url, request, String.class)).isEqualTo("succesfully registered");
    }

    @Test
    public void duplicatedUsernameTest() throws Exception {
        testService.join(new User("aaa", "bbb"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("username", "aaa");
        body.put("password", "bbb");

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        String url = "http://localhost:" + port + "/register";
        assertThat(this.restTemplate.postForObject(url, request, String.class)).contains("register failed");
    }

    @Test
    @Transactional
    @Rollback
    public void loginTest() throws Exception {
        testService.join(new User("aaa", "bbb"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("username", "aaa");
        body.put("password", "bbb");

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        String url = "http://localhost:" + port + "/login";
        assertThat(this.restTemplate.postForObject(url, request, String.class)).contains("login success");
    }

    @Test
    public void UsernameNotFoundTest() throws Exception {
        testService.join(new User("aaa", "bbb"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("username", "ccc");
        body.put("password", "ddd");

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        String url = "http://localhost:" + port + "/login";
        assertThat(this.restTemplate.postForObject(url, request, String.class)).contains("login failed");
    }

    @Test
    public void IncorrectPasswordTest() throws Exception {
        testService.join(new User("aaa", "bbb"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("username", "aaa");
        body.put("password", "ccc");

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        String url = "http://localhost:" + port + "/login";
        assertThat(this.restTemplate.postForObject(url, request, String.class)).contains("login failed");
    }
}