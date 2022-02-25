package com.sparta.magazine.repository;

import com.sparta.magazine.entity.Like;
import com.sparta.magazine.entity.Post;
import com.sparta.magazine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Optional<Post> post);
}
