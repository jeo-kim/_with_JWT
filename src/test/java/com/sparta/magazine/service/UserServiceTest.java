package com.sparta.magazine.service;

import com.sparta.magazine.dto.UserDto;
import com.sparta.magazine.entity.Authority;
import com.sparta.magazine.entity.User;
import com.sparta.magazine.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("중복 이메일 불가")
    void duplicatedEmail() {

        User user1 = createUser("duplicatedEmail");
        UserDto userDto = new UserDto("duplicatedEmail", "testPassword", "testNickname");

        Exception e = assertThrows(Exception.class, () ->
                userService.signup(userDto));

        assertEquals("이미 존재하는 이메일입니다.", e.getMessage());

    }

    private User createUser(String username) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(username)
                .password("12345")
                .nickname("test")
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        userRepository.save(user);
        return user;
    }
}