package com.sparta.magazine.controller;


import com.sparta.magazine.dto.LoginDto;
import com.sparta.magazine.dto.TokenDto;
import com.sparta.magazine.entity.User;
import com.sparta.magazine.jwt.JwtFilter;
import com.sparta.magazine.jwt.TokenProvider;
import com.sparta.magazine.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserService userService;


    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        User user = userService.getMyUserWithAuthorities().get();
        String userId = String.valueOf(user.getUserId());
        String userEmail = user.getUsername();
        String nickname = user.getNickname();

        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("token", jwt);
        userInfo.put("userId", userId);
        userInfo.put("userEmail", userEmail);
        userInfo.put("nickname", nickname);

        return ResponseEntity.ok().body(userInfo);
//        return new ResponseEntity<userIn>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}