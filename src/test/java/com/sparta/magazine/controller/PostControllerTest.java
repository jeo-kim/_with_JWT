//package com.sparta.magazine.controller;
//
//import com.sparta.magazine.entity.Authority;
//import com.sparta.magazine.entity.User;
//import com.sparta.magazine.service.PostService;
//import com.sparta.magazine.service.UserService;
//import com.sparta.magazine.validator.PostInputValidator;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.RequestBuilder;
//
//import java.util.Optional;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anySet;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureWebMvc
//@SpringBootTest
//class PostControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    PostService postService;
//
//    @MockBean
//    UserService userService;
//    @MockBean
//    PostInputValidator postInputValidator;
//
//    @Test
//    @DisplayName("로그인하지 않으면 게시글 작성 X.")
//    public void getSinglePost() throws Exception {
//        //given
//        given(userService.getMyUserWithAuthorities()).willReturn(
//                Optional.of(new User(33L, "고구마", "asdf", "감자와 고구마", true, anySet()))
//        );
//
//        //andExpect
//        mockMvc.perform(
//                        (RequestBuilder) post("/api/post"))
//                .andExpect(status().isOk());
//
//        //verify
//        verify(userService).getMyUserWithAuthorities();
//
//    }
//
//}