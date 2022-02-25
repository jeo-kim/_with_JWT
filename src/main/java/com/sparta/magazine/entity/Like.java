package com.sparta.magazine.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.magazine.dto.LikeRequestDto;
import lombok.Getter;

import javax.persistence.*;


@Getter
//@NoArgsConstructor
@Entity
@Table(name = "LIKES")
public class Like extends Timestamped{

    protected Like() {}

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fkey_user_id"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fkey_post_id"))
    private Post post;

    public Like(LikeRequestDto requestDto) {
        this.user = requestDto.getUser();
        this.post = requestDto.getPost();
    }

}
