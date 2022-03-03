package com.sparta.magazine.service;


import java.util.Collections;
import java.util.Optional;
import com.sparta.magazine.dto.UserDto;
import com.sparta.magazine.entity.Authority;
import com.sparta.magazine.entity.User;
//import com.sparta.magazine.exception.DuplicateMemberException;
import com.sparta.magazine.repository.UserRepository;
import com.sparta.magazine.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User signup(UserDto userDto) {

        if (userRepository.findOneByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

//        return UserDto.from(userRepository.save(user));
        return userRepository.save(user);

    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
//        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "user_info")
    public Optional<User> getMyUserWithAuthorities() {
//        return UserDto.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null));
        log.info("로그인한 유저의 정보를 조회합니다.");
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
}