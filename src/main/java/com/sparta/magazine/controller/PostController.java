package com.sparta.magazine.controller;


import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.entity.User;
import com.sparta.magazine.service.PostService;
import com.sparta.magazine.service.UserService;
import com.sparta.magazine.validator.PostInputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final UserService userService;
    private final PostService postService;
    private final PostInputValidator postInputValidator;

    @PostMapping("/post")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PostRequestDto> createPost(
            @Valid @RequestBody PostRequestDto postRequestDto
    ) {
        postInputValidator.areValidInputs(postRequestDto);
        User user = userService.getMyUserWithAuthorities().get();
        postService.createPost(user, postRequestDto);
        return ResponseEntity.ok().body(postRequestDto);
    }


//    @PostMapping("/post")
//    public Long createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if (userDetails == null) {
//            throw new IllegalArgumentException("로그인하지 않은 사용자는 포스팅할 수 없습니다.");
//        }
//        postInputValidator.areValidInputs(requestDto);
//        User user = userDetails.getUser();
//        Long savedId = postService.createPost(user, requestDto);
//        return savedId;
//    }
}
