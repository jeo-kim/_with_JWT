package com.sparta.magazine.service;

import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.entity.LayoutType;
import com.sparta.magazine.entity.Post;
import com.sparta.magazine.entity.User;
import com.sparta.magazine.repository.LikeRepository;
import com.sparta.magazine.repository.PostRepository;
import com.sparta.magazine.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeService likeService;

    @BeforeEach
    void resetRepository() {
        userRepository.deleteAll();
        likeRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("좋아요 추가/취소")
    public void addLike() throws Exception {
        //given
        User user = new User();
        user.setUserId(1L);
        user.setUsername("test2");
        userRepository.save(user);

        Post post = new Post(user, createPostRequestDto());
        postRepository.save(post);

        //when
        String message1 = likeService.createLike(user, post);
        String message2 = likeService.createLike(user, post);

        //then
        assertEquals("좋아요 추가완료", message1);
        assertEquals("좋아요 취소완료", message2);

    }



    private PostRequestDto createPostRequestDto() {
        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/cloneinsta-9ee36.appspot.com/o/images%2F9_1645896955937?alt=media&token=1ca2b3f1-8e04-4911-9342-871631fb922e";
        String contents = "글 작성 내용";
        LayoutType layoutType = LayoutType.valueOf("LEFT");
        return new PostRequestDto(imageUrl, contents, layoutType);
    }

}



