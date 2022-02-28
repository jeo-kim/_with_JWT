package com.sparta.magazine.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.entity.LayoutType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostInputValidatorTest {

    @Autowired
    PostInputValidator postInputValidator;

    @Test
    @DisplayName("POST 형태: 정상")
    void postInputValidator1() {
        // given
        PostRequestDto requestDto = new PostRequestDto(
                "https://firebasestorage.googleapis.com/v0/b/cloneinsta-9ee36.appspot.com/o/images%2FNliT0arDIZWXN4ZYizriHqIFfIo1_1645515492237?alt=media&token=d91c1923-7a44-406c-bb8c-3aecaa55c2dd",
                "정상적인 글",
                LayoutType.DEFAULT
        );
        // when
        postInputValidator.areValidInputs(requestDto);
        // then
    }

    @Test
    @DisplayName("POST 형태: 빈문자열 contents")
    public void postInputValidator2() {
        // given
        PostRequestDto requestDto = new PostRequestDto(
                "https://firebasestorage.googleapis.com/v0/b/cloneinsta-9ee36.appspot.com/o/images%2FNliT0arDIZWXN4ZYizriHqIFfIo1_1645515492237?alt=media&token=d91c1923-7a44-406c-bb8c-3aecaa55c2dd",
                "",
                LayoutType.DEFAULT
        );
        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            postInputValidator.areValidInputs(requestDto);
        });

        // then
        assertEquals("글을 작성해주세요.", exception.getMessage());
    }

}