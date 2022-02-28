package com.sparta.magazine.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignupInputValidatorTest {

    @Autowired
    SignupInputValidator signupInputValidator;

    @Test
    @DisplayName("회원정보 정상")
    void signupInputValidator1() {
        // given
        String userEmail = "abc@naver.com";
        String password = "123456";
        String nickname = "happy";
        // when
        signupInputValidator.IsValidSignupInput(userEmail, password, nickname);
        // then
    }

    @Test
    @DisplayName("회원정보 비정상: 닉네임 3자 미만")
    public void signupInputValidator2() {
        // given
        String userEmail = "abc@naver.com";
        String password = "123456";
        String nickname = "ha";
        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            signupInputValidator.IsValidSignupInput(userEmail, password, nickname);
        });

        // then
        assertEquals("닉네임은 최소 3자 이상이어야 합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("회원정보 비정상: 비밀번호에 닉네임 포함")
    public void signupInputValidator3() {
        // given
        String userEmail = "abc@naver.com";
        String password = "hahahaha";
        String nickname = "hahaha";
        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            signupInputValidator.IsValidSignupInput(userEmail, password, nickname);
        });

        // then
        assertEquals("비밀번호에 닉네임이 포함될 수 없습니다.", exception.getMessage());
    }

}