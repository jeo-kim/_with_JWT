package com.sparta.magazine.controller;

import com.sparta.magazine.entity.Post;
import com.sparta.magazine.entity.User;
import com.sparta.magazine.repository.PostRepository;
import com.sparta.magazine.service.LikeService;
import com.sparta.magazine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LikeController {

    private final UserService userService;
    private final LikeService likeService;
    private final PostRepository postRepository;

    @GetMapping("/post/{post_id}/like")
    public ResponseEntity<HashMap> createLike(@PathVariable Long post_id) {
        if (!userService.getMyUserWithAuthorities().isPresent()) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        Optional<Post> optionalPost = postRepository.findById(post_id);
        if (!optionalPost.isPresent()) {
            throw new IllegalArgumentException("해당 postId의 게시물이 존재하지 않습니다.");
        }

        User user = userService.getMyUserWithAuthorities().get();
        Post post = optionalPost.get();
        // service 에게 작업 넘기고 적절한 메시지 돌려받는다.(좋아요 추가한 건지, 취소한건지)
        String message = likeService.createLike(user, post);
        int newLikeCnt= post.getLikes().size();
        HashMap<String, String> data = new HashMap<>();
        data.put("message", message);
        data.put("newLikeCnt", String.valueOf(newLikeCnt));

        return ResponseEntity.ok().body(data);


    }

}