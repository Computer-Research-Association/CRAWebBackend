package com.handong.cra.crawebbackend.board.domain;

import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.comment.domain.Comment;
import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//@Setter
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
    @ManyToOne
    @JoinColumn(name = "havruta_id") // 외래 키 컬럼을 설정
    private Havruta havruta;


    @Column(name = "like_count")
    private Long likeCount;

    private Long view;

    public Board(User user, String title, Category category, String content, List<String> imageUrls) {
        this.user = user;
        this.title = title;
        this.category = category;
        this.content = content;
        this.imageUrls = imageUrls;
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

    public static Board of(User user, CreateBoardDto createBoardDto) {
        return new Board(user, createBoardDto);

    }

    // TODO : 수정할 데이터 추가
    public void update(UpdateBoardDto updateBoardDto) {
        this.title = updateBoardDto.getTitle();
        this.content = updateBoardDto.getContent();
        this.imageUrls = updateBoardDto.getImageUrls();
    }

    public void increaseView() {
        this.view++;
    }

}
