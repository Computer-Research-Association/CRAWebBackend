package com.handong.cra.crawebbackend.board.dto;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.havruta.dto.HavrutaDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ListBoardDto {
    private Long id;
    private Long userId;
    private String title;
    private UserDetailDto userDetailDto = new UserDetailDto();
    private String content;
    private Category category;
    private Boolean deleted;
    private Long likeCount;
    private Long view;

    private HavrutaDto havrutaDto;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public ListBoardDto(Board board) {
        this.id = board.getId();
        this.userId = board.getUser().getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.likeCount = (long) board.getLikedUsers().size();
        this.view = board.getView();
        if (board.getHavruta() != null)
            this.havrutaDto = HavrutaDto.from(board.getHavruta());
        this.userDetailDto = UserDetailDto.from(board.getUser());

        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }

    public static ListBoardDto from(Board board) {
        if (board.getDeleted())return null;
        return new ListBoardDto(board);
    }
}
