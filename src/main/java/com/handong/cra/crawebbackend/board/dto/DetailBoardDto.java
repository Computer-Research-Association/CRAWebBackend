package com.handong.cra.crawebbackend.board.dto;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.havruta.dto.HavrutaDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DetailBoardDto {
    private Long id;
    private Long userId;
    private UserDetailDto userDetailDto;
    private String title;
    private String content;
    private Category category;
    private Long likeCount;
    private Long view;
    private List<String> imageUrls;
    private String fileUrl;
    //havruta
    private HavrutaDto havrutaDto;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Boolean viewerLiked;

    public DetailBoardDto(Board board) {
        this.id = board.getId();
        this.userId = board.getUser().getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.likeCount = (long) board.getLikedUsers().size();
        this.view = board.getView();
        this.imageUrls = board.getImageUrls();
        this.fileUrl = board.getFileUrl();

        if (board.getHavruta() != null)
            this.havrutaDto = HavrutaDto.from(board.getHavruta());
        this.userDetailDto = UserDetailDto.from(board.getUser());

        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }

    public DetailBoardDto(Board board, Boolean viewerLiked) {
        this(board);
        this.viewerLiked = viewerLiked;
    }

    public static DetailBoardDto from(Board board) {
        return new DetailBoardDto(board);
    }

    public static DetailBoardDto from(Board board, Boolean viewerLiked) {
        return new DetailBoardDto(board, viewerLiked);
    }

}
