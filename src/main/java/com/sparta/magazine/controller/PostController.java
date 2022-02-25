package com.sparta.magazine.controller;


import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.dto.PostResponseDto;
import com.sparta.magazine.entity.User;
import com.sparta.magazine.service.PostService;
import com.sparta.magazine.service.UserService;
import com.sparta.magazine.validator.PostInputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final UserService userService;
    private final PostService postService;
    private final PostInputValidator postInputValidator;

    @PostMapping("/post")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PostRequestDto> createPost(
            @Valid @RequestBody PostRequestDto postRequestDto
    ) {
        if (!userService.getMyUserWithAuthorities().isPresent()) {
            throw new IllegalArgumentException("로그인하지 않은 사용자는 포스팅할 수 없습니다.");
        }
        postInputValidator.areValidInputs(postRequestDto);
        User user = userService.getMyUserWithAuthorities().get();
        postService.createPost(user, postRequestDto);
        return ResponseEntity.ok().body(postRequestDto);
    }

    @GetMapping("/post")
    public List<PostResponseDto> getAllPosts(Pageable pageable) {
        Long userId = Long.valueOf(-1);
        if (userService.getMyUserWithAuthorities().isPresent()) {
            userId = userService.getMyUserWithAuthorities().get().getUserId();
        }
        List<PostResponseDto> postsToFE = postService.getAllPosts(userId, pageable);
        return postsToFE;
    }

    @GetMapping("post/{postId}")
    public PostResponseDto getSinglePost(@PathVariable Long postId) {
        Long userId = Long.valueOf(-1);
        if (userService.getMyUserWithAuthorities().isPresent()) {
            userId = userService.getMyUserWithAuthorities().get().getUserId();
        }
        PostResponseDto responseDto = postService.getSinglePost(postId, userId);
        return responseDto;
    }

}
