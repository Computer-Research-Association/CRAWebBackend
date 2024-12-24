package com.handong.cra.crawebbackend.board.domain;

import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import com.handong.cra.crawebbackend.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column(length = 100, nullable = false)
    private String title;

    private Category category;

    @Column(length = 2048, nullable = false)
    private String content;

    @ElementCollection
    @CollectionTable(name = "board_images", joinColumns = @JoinColumn(name = "board_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    // TODO: havruta_id 추가

    @Column(name = "like_count")
    private Long likeCount;

    private Long view;

    public Board(User user, String title, Category category, String content, List<String> imageUrls) {
        this.user = user;
        this.title = title;
        this.category = category;
        this.content = content;
        likeCount = 0L;
        view = 0L;
    }

    public Board(User user, CreateBoardDto createBoardDto) {
        this.user = user;
        this.title = createBoardDto.getTitle();
        this.category = createBoardDto.getCategory();
        this.content = createBoardDto.getContent();
        this.imageUrls = createBoardDto.getImageUrls();
        likeCount = 0L;
        view = 0L;
    }
}
