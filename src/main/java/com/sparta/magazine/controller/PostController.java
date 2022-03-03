package com.sparta.magazine.controller;


import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.dto.PostResponseDto;
import com.sparta.magazine.entity.User;
import com.sparta.magazine.service.PostService;
import com.sparta.magazine.service.UserService;
import com.sparta.magazine.validator.PostInputValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class PostController {

    private final UserService userService;
    private final PostService postService;
    private final PostInputValidator postInputValidator;

    @PostMapping("/post")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PostRequestDto> createPost(
            @Valid @RequestBody PostRequestDto postRequestDto
    ) {
        checkLogin("포스팅을 위해서는 로그인이 필요합니다.");
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
        log.info("단일 게시글의 정보를 출력합니다.");
        return responseDto;
    }

    @PutMapping("post/{postId}")
    public ResponseEntity<Long> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        checkLogin("수정을 위해서는 로그인이 필요합니다.");
        postInputValidator.areValidInputs(requestDto);
        User user = userService.getMyUserWithAuthorities().get();
        postService.update(postId, user, requestDto);
        return ResponseEntity.ok().body(postId);
    };

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        checkLogin("삭제를 위해서는 로그인이 필요합니다.");
        User user = userService.getMyUserWithAuthorities().get();
        String deletedImageUrl = postService.deletePost(postId, user);
        return ResponseEntity.ok().body(deletedImageUrl);

    }

    private void checkLogin(String s) {
        if (!userService.getMyUserWithAuthorities().isPresent()) {
            throw new IllegalArgumentException(s);
        }
    }


}
