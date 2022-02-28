package com.sparta.magazine.service;

import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.dto.PostResponseDto;
import com.sparta.magazine.entity.*;
import com.sparta.magazine.repository.PostRepository;
import com.sparta.magazine.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void resetRepository() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("포스팅 성공")
    void postSuccess() throws Exception {

        //given
        User user = createUser();
        PostRequestDto requestDto = createPostRequestDto();

        //when
        Post post = new Post(user, requestDto);
        Post save = postRepository.save(post);

        //then
        assertEquals(user.getUserId(), save.getUser().getUserId());
        assertEquals(requestDto.getImageUrl(), save.getImageUrl());
        assertEquals(requestDto.getContents(), save.getContents());
        assertEquals(requestDto.getLayoutType(), save.getLayoutType());
    }


    @Test
    @DisplayName("로그인한 사용자의 글이면 isMe = true 이다.")
    void getMyPost() throws Exception {
        //given
        User user = createUser();
        Post post = new Post(user, createPostRequestDto());

        //when
        PostResponseDto responseDto = postService.convertToPostFE(user.getUserId(), post);

        //then
        Assertions.assertThat(responseDto.getIsMe()).isTrue();
    }

    @Test
    @DisplayName("로그인한 사용자의 글이 아니면 isMe = false 이다.")
    void getOthersPost() throws Exception {
        //given
        User user1 = new User(); // 로그인한 유저
        user1.setUserId(1L);

        User user2 = new User(); // 글 작성자
        user2.setUserId(2L);

        Post post = new Post(user2, createPostRequestDto());

        //when
        PostResponseDto responseDto = postService.convertToPostFE(user1.getUserId(), post);

        //then
        Assertions.assertThat(responseDto.getIsMe()).isFalse();
    }

    @Test
    @DisplayName("작성자가 아닌 사용자는 삭제할 수 없다.")
    public void cannotDelete() throws Exception {
        //given
        User user1 = new User(); // 로그인한 유저
        user1.setUserId(-1L);

        User user2 = createUser();
        Post post = createPost(user2);

        //when
        Exception e = assertThrows(Exception.class, () ->
                postService.deletePost(post.getId(), user1));

        //then
        assertEquals("작성자가 아니면 삭제할 수 없습니다.", e.getMessage());
    }

    private Post createPost(User user2) {
        PostRequestDto postRequestDto = createPostRequestDto();
        Post post = new Post(user2, postRequestDto);
        postRepository.save(post);
        return post;
    }


    private User createUser() {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username("test")
                .password("12345")
                .nickname("test")
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        userRepository.save(user);
        return user;
    }

    private PostRequestDto createPostRequestDto() {
        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/cloneinsta-9ee36.appspot.com/o/images%2F9_1645896955937?alt=media&token=1ca2b3f1-8e04-4911-9342-871631fb922e";
        String contents = "글 작성 내용";
        LayoutType layoutType = LayoutType.valueOf("LEFT");
        return new PostRequestDto(imageUrl, contents, layoutType);
    }

}