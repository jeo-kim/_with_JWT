package com.sparta.magazine.controller;


import com.sparta.magazine.dto.UserDto;
import com.sparta.magazine.entity.User;
import com.sparta.magazine.service.UserService;
import com.sparta.magazine.validator.SignupInputValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final SignupInputValidator signupInputValidator;

    public UserController(UserService userService, SignupInputValidator signupInputValidator) {

        this.userService = userService;
        this.signupInputValidator = signupInputValidator;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        String userEmail = userDto.getUsername();
        String password = userDto.getPassword();
        String nickname = userDto.getNickname();

        signupInputValidator.IsValidSignupInput(userEmail, password, nickname);
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

}