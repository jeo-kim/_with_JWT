//package com.sparta.magazine.service;
//
//import com.sparta.magazine.dto.PostRequestDto;
//import com.sparta.magazine.entity.Authority;
//import com.sparta.magazine.entity.LayoutType;
//import com.sparta.magazine.entity.Post;
//import com.sparta.magazine.entity.User;
//import com.sparta.magazine.repository.LikeRepository;
//import com.sparta.magazine.repository.PostRepository;
//import com.sparta.magazine.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.persistence.EntityManager;
//import java.util.Collections;
//import java.util.Optional;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//class LikeServiceTest {
//
//    @Autowired
//    LikeRepository likeRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    PostRepository postRepository;
//
//    @Autowired
//    LikeService likeService;
//
//    @BeforeEach
//    void resetRepository() {
//        userRepository.deleteAll();
//        likeRepository.deleteAll();
//    }
//
//    @Test
//    public void addLike() throws Exception {
//        //given
//        User user = new User();
//        user.setUserId(1L);
//        user.setUsername("test2");
//        userRepository.save(user);
//
//        Post post = new Post(user, createPostRequestDto());
//        Post save = postRepository.save(post);
//
//        Optional<Post> byId = postRepository.findById(save.getId());
//        System.out.println("byId = " + byId.get().getUser().getUsername());
//
//        String username = userRepository.findById(user.getUserId()).get().getUsername();
//        System.out.println("username = " + username);
//
//    }
//
//
//
//    private PostRequestDto createPostRequestDto() {
//        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/cloneinsta-9ee36.appspot.com/o/images%2F9_1645896955937?alt=media&token=1ca2b3f1-8e04-4911-9342-871631fb922e";
//        String contents = "글 작성 내용";
//        LayoutType layoutType = LayoutType.valueOf("LEFT");
//        return new PostRequestDto(imageUrl, contents, layoutType);
//    }
//
//}
//
//
//
