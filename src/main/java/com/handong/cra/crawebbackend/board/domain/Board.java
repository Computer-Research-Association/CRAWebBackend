package com.handong.cra.crawebbackend.board.domain;

import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.CreateHavrutaBoardDto;
import com.handong.cra.crawebbackend.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    @Setter
    private List<String> imageUrls = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "havruta_id") // 외래 키 컬럼을 설정
    private Havruta havruta;


    @ManyToMany(mappedBy = "likedBoards")
    private List<User> likedUsers = new ArrayList<>();

    private Long view;

    public Board(User user, String title, Category category, String content, List<String> imageUrls) {
        this.user = user;
        this.title = title;
        this.category = category;
        this.content = content;
        this.imageUrls = imageUrls;
        view = 0L;
    }

    public Board(User user, CreateBoardDto createBoardDto) {
        this.user = user;
        this.title = createBoardDto.getTitle();
        this.category = createBoardDto.getCategory();
        this.content = createBoardDto.getContent();
        this.imageUrls = createBoardDto.getImageUrls();
        view = 0L;
    }

    public Board(User user, Havruta havruta, CreateHavrutaBoardDto createHavrutaBoardDto) {
      this(user, CreateBoardDto.from(createHavrutaBoardDto, user.getId()));
      this.havruta = havruta;
    }

    public static Board of(User user, CreateBoardDto createBoardDto) {
        return new Board(user, createBoardDto);

    }
    public static Board of(User user, Havruta havruta, CreateHavrutaBoardDto createHavrutaBoardDto) {
        return new Board(user, havruta, createHavrutaBoardDto);
    }



    // TODO : 수정할 데이터 추가
    public Board update(UpdateBoardDto updateBoardDto) {
        this.title = updateBoardDto.getTitle();
        this.content = updateBoardDto.getContent();
        this.imageUrls = updateBoardDto.getImageUrls();
        return this;
    }

    public void increaseView() {
        this.view++;
    }

    public void like(User user) {
        this.likedUsers.add(user);
    }

    public void unlike(User user) {
        this.likedUsers.remove(user);
    }

}
