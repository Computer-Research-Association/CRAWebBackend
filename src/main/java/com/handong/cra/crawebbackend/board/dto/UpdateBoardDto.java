package com.handong.cra.crawebbackend.board.dto;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.dto.request.ReqUpdateBoardDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateBoardDto {

    private Long id;
    private Long userId;
    private Boolean deleted;
    private String title;
    private String content;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    // TODO: add havrutaid


    public UpdateBoardDto(Long id, ReqUpdateBoardDto reqUpdateBoardDto) {
        // TODO : User Logic
        this.userId = reqUpdateBoardDto.getUserId();

        this.id = id;
        this.deleted = reqUpdateBoardDto.getDeleted();
        this.title = reqUpdateBoardDto.getTitle();
        this.content = reqUpdateBoardDto.getContent();
        this.imageUrls = reqUpdateBoardDto.getImageUrls();
    }

    public UpdateBoardDto(Board board) {
        this.id = board.getId();
        this.userId = board.getUser().getId();
        this.deleted = board.getDeleted();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.imageUrls = board.getImageUrls();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }

    public static UpdateBoardDto of(Long id, ReqUpdateBoardDto reqUpdateBoardDto) {
        return new UpdateBoardDto(id, reqUpdateBoardDto);
    }


    public static UpdateBoardDto from(Board board) {
        return new UpdateBoardDto(board);
    }
}
