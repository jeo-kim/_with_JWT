package com.sparta.magazine.service;

import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.dto.PostResponseDto;
import com.sparta.magazine.entity.LayoutType;
import com.sparta.magazine.entity.Like;
import com.sparta.magazine.entity.Post;
import com.sparta.magazine.entity.User;
import com.sparta.magazine.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long createPost(User user, PostRequestDto postRequestDto) {
        Post post = new Post(user, postRequestDto);
        Post save = postRepository.save(post);
        return save.getId();
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts(Long userId, Pageable pageable) {

        List<Post> posts = postRepository.findAllFetched(pageable);
        List<PostResponseDto> postsToFE = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDto postToFE = convertToPostFE(userId, post);
            postsToFE.add(postToFE);
        }
        return postsToFE;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getSinglePost(Long post_id, Long userId) {
        Optional<Post> optionalPost = postRepository.findById(post_id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            PostResponseDto postToFE = convertToPostFE(userId, post);
            return postToFE;
        } else {
            throw new IllegalArgumentException("해당 postId의 게시물이 존재하지 않습니다.");
        }
    }

    @Transactional
    public Long update(Long postId, User user, PostRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 postId가 존재하지 않습니다.")
        );
        User originUser = post.getUser();
        if (!Objects.equals(user.getUserId(), originUser.getUserId())) {
            throw new IllegalArgumentException("작성자가 아니면 수정할 수 없습니다.");
        }
        post.update(user, requestDto);
        return post.getId();
    }

    @Transactional
    public String deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 postId가 존재하지 않습니다.")
        );
        User originUser = post.getUser();
        if (!Objects.equals(user.getUserId(), originUser.getUserId())) {
            throw new IllegalArgumentException("작성자가 아니면 삭제할 수 없습니다.");
        }
        String imageUrl = post.getImageUrl();
        postRepository.deleteById(postId);
        return imageUrl;
    }

    //프론트에서 보여줄 양식대로 재편성하는 작업(닉네임, 좋아요 수, 각 게시물과 로그인한 사용자의 좋아요 관계 등)
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
