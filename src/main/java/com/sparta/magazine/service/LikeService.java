package com.sparta.magazine.service;

import com.sparta.magazine.dto.LikeRequestDto;
import com.sparta.magazine.entity.Like;
import com.sparta.magazine.entity.Post;
import com.sparta.magazine.entity.User;
import com.sparta.magazine.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    public String createLike(User user, Post post) {
        //이 user 가 이 post 에 대한 like 한적 있는지 likeRepository 에서 찾아보기
        Optional<Like> optionalLike = likeRepository.findByUserAndPost(user, Optional.ofNullable(post));

        if (optionalLike.isPresent()) {
            // 이미 like 존재하던 것이었다면, 해당 like entity 를 삭제하여 좋아요 취소 처리
            Long likeId = optionalLike.get().getId();
            likeRepository.deleteById(likeId);
            return "좋아요 취소완료";

        } else {
            // dto 통해서 like 만들고 repository 에 save.
            LikeRequestDto requestDto = new LikeRequestDto(user, post);
            Like like = new Like(requestDto);
            Like save = likeRepository.save(like);
            return "좋아요 추가완료";
        }
    }

}
