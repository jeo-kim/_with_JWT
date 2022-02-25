package com.sparta.magazine.validator;

import org.springframework.stereotype.Component;

@Component
public class SignupInputValidator {
    public void IsValidSignupInput(String userEmail, String password, String nickname) {
        //이메일 예외처리
        if (userEmail.length() == 0) {
            throw new IllegalArgumentException("이메일을 입력해주세요");
        }
        //닉네임 예외처리
        if (!nickname.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")) {
            throw new IllegalArgumentException("닉네임에 특수문자는 포함할 수 없습니다.");
        }
        if (nickname.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            throw new IllegalArgumentException("닉네임에 한글은 포함할 수 없습니다.");
        }
        if (nickname.length() < 3) {
            throw new IllegalArgumentException("닉네임은 최소 3자 이상이어야 합니다.");
        }
        //비밀번호 예외처리
        if (password.contains(nickname)) {
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함될 수 없습니다.");
        }
        if (password.length() < 5) {
            throw new IllegalArgumentException("비밀번호는 5자 이상이어야 합니다.");
        }
    }
}
