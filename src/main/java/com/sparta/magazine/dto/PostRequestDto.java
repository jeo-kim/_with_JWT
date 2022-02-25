package com.sparta.magazine.dto;

import com.sparta.magazine.entity.LayoutType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@RequiredArgsConstructor
@Getter
@Setter
public class PostRequestDto {
    private final String imageUrl;
    private final String contents;
    @Enumerated(EnumType.STRING)
    private final LayoutType layoutType;
}
