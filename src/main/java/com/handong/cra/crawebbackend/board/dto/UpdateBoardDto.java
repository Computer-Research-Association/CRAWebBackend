package com.handong.cra.crawebbackend.board.dto;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.dto.request.ReqUpdateBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.request.ReqUpdateHavrutaBoardDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    private List<MultipartFile> files;
    private List<String> fileUrls;

    public UpdateBoardDto(Long userId, Long boardId, ReqUpdateBoardDto reqUpdateBoardDto, List<MultipartFile> files) {
        this.id = boardId;
        this.userId = userId;
        this.deleted = reqUpdateBoardDto.getDeleted();
        this.title = reqUpdateBoardDto.getTitle();
        this.content = reqUpdateBoardDto.getContent();
        this.imageUrls = reqUpdateBoardDto.getImageUrls();
        this.files = files;
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

    public UpdateBoardDto(Long userId, ReqUpdateHavrutaBoardDto reqUpdateHavrutaBoardDto) {
        this.userId = userId;
        this.title = reqUpdateHavrutaBoardDto.getTitle();
        this.content = reqUpdateHavrutaBoardDto.getContent();
        this.imageUrls = reqUpdateHavrutaBoardDto.getImageUrls();
    }

    public static UpdateBoardDto of(Long userId, Long boardId, ReqUpdateBoardDto reqUpdateBoardDto, List<MultipartFile> files) {
        return new UpdateBoardDto(userId, boardId, reqUpdateBoardDto, files);
    }


    public static UpdateBoardDto from(Board board) {
        return new UpdateBoardDto(board);
    }
}
