package com.sparta.magazine.dto;

import com.sparta.magazine.entity.LayoutType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class PostResponseDto {
    private final Long postId;
    private final String nickname;
    private final String createdAt;
    private final String contents;
    private final String imageUrl;
    private final Long likeCnt;
    private final Boolean userLiked;
    private final LayoutType layoutType;
    private final Boolean isMe;
}
