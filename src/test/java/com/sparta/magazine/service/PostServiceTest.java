package com.sparta.magazine.service;

import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.dto.PostResponseDto;
import com.sparta.magazine.entity.LayoutType;
import com.sparta.magazine.entity.Like;
import com.sparta.magazine.entity.Post;
import com.sparta.magazine.entity.User;
import com.sparta.magazine.repository.PostRepository;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

//    @Test
//    @DisplayName("포스팅 성공")
//    void postSuccess() throws Exception {
//        //given
//        User user = new User();
//        user.setUserId(1L);
//        user.setUsername("a@a.a");
//        user.setNickname("aaa");
//        PostRequestDto requestDto = createPostRequestDto();
//
//        //when
//        Post post = new Post(user, requestDto);
//        Post save = postRepository.save(post);
//
//        //then
//        assertEquals(user.getUserId(), save.getUser().getUserId());
//        assertEquals(requestDto.getImageUrl(), save.getImageUrl());
//        assertEquals(requestDto.getContents(), save.getContents());
//        assertEquals(requestDto.getLayoutType(), save.getLayoutType());
//    }


    @Test
    @DisplayName("로그인한 사용자의 글이면 isMe = true 이다.")
    void getMyPost() throws Exception {
        //given
        User user = new User();
        user.setUserId(1L);
        Post post = new Post(user, createPostRequestDto());

        //when
        PostResponseDto responseDto = convertToPostFE(user.getUserId(), post);

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
        PostResponseDto responseDto = convertToPostFE(user1.getUserId(), post);

        //then
        Assertions.assertThat(responseDto.getIsMe()).isFalse();
    }

    @Test
    @DisplayName("작성자가 아닌 사용자는 삭제할 수 없다.")
    public void cannotDelete() throws Exception {
        //given
//        User user1 = new User(); // 로그인한 유저
//        user1.setUserId(-1L);
////
//
//        //when
//        Exception e = assertThrows(Exception.class, () ->
//                postService.deletePost(post.getId(), user1));

        //then
//        assertEquals("작성자가 아니면 삭제할 수 없습니다.", e.getMessage());
    }



    private PostRequestDto createPostRequestDto() {
        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/cloneinsta-9ee36.appspot.com/o/images%2F9_1645896955937?alt=media&token=1ca2b3f1-8e04-4911-9342-871631fb922e";
        String contents = "글 작성 내용";
        LayoutType layoutType = LayoutType.valueOf("LEFT");
        return new PostRequestDto(imageUrl, contents, layoutType);
    }



    private PostResponseDto convertToPostFE(Long userId, Post post) {
        Long postId = post.getId();
        String nickname = post.getUser().getNickname();
        String createdAt = String.valueOf(post.getCreatedAt());
        String contents = post.getContents();
        String imageUrl = post.getImageUrl();
        LayoutType layoutType = post.getLayoutType();
        Boolean isMe = (Objects.equals(post.getUser().getUserId(), userId));

        List<Like> likes = post.getLikes();
        Long likeCnt = Long.valueOf(post.getLikes().size());
        Boolean userLiked = false;
        for (Like like : likes) {
            if (like.getUser().getUserId() == userId) {
                userLiked = true;
                break;
            }
        }
        PostResponseDto postToFE = new PostResponseDto(postId, nickname, createdAt, contents, imageUrl, likeCnt, userLiked, layoutType, isMe);
        return postToFE;
    }
}