package com.sparta.magazine.dto;

import com.sparta.magazine.entity.Post;
import com.sparta.magazine.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class LikeRequestDto {
    private final User user;
    private final Post post;

}
