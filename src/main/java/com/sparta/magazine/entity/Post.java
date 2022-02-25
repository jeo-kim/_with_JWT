package com.sparta.magazine.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.entity.LayoutType;
import com.sparta.magazine.entity.Like;
import com.sparta.magazine.entity.Timestamped;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "POSTS")
public class Post extends Timestamped {

    protected Post() {}

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LayoutType layoutType;

    @BatchSize(size = 500)
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    public Post(User user, PostRequestDto requestDto) {
        this.user = user;
        this.contents = requestDto.getContents();
        this.imageUrl = requestDto.getImageUrl();
        this.layoutType = requestDto.getLayoutType();
    }

    public void update(User user, PostRequestDto requestDto) {
        this.user = user;
        this.contents = requestDto.getContents();
        this.imageUrl = requestDto.getImageUrl();
        this.layoutType = requestDto.getLayoutType();
    }
}