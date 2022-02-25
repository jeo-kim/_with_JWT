package com.sparta.magazine.validator;

import com.sparta.magazine.dto.PostRequestDto;
import org.springframework.stereotype.Component;

@Component
public class PostInputValidator {
    public void areValidInputs(PostRequestDto postRequestDto) {
        String contents = postRequestDto.getContents().trim();
        String imageUrl = postRequestDto.getImageUrl().trim();
        if (contents.length() == 0 || imageUrl.length() == 0) {
            throw new IllegalArgumentException("글을 작성해주세요.");
        }

    }
}
