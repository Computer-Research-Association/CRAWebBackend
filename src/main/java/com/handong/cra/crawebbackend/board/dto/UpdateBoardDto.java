package com.handong.cra.crawebbackend.board.dto;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.dto.request.ReqUpdateBoardDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
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
    private UserDetailDto userDetailDto;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MultipartFile file;
    private String fileUrl;
    private Boolean isChangedFile;

    public UpdateBoardDto(Long userId, Long boardId, ReqUpdateBoardDto reqUpdateBoardDto, MultipartFile file) {
        this.id = boardId;
        this.userId = userId;
        this.deleted = reqUpdateBoardDto.getDeleted();
        this.title = reqUpdateBoardDto.getTitle();
        this.content = reqUpdateBoardDto.getContent();
        this.imageUrls = reqUpdateBoardDto.getImageUrls();
        this.file = file;
        this.isChangedFile = reqUpdateBoardDto.getIsChangedFile();
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
        this.userDetailDto = UserDetailDto.from(board.getUser());
    }

    public UpdateBoardDto(Long userId, Long boardId) {
        this.userId = userId;
        this.id = boardId;
    }

    public static UpdateBoardDto of(Long userId, Long boardId, ReqUpdateBoardDto reqUpdateBoardDto, MultipartFile file) {
        return new UpdateBoardDto(userId, boardId, reqUpdateBoardDto, file);
    }

    public static UpdateBoardDto of (Long userId, Long boardId){
        return new UpdateBoardDto(userId, boardId);
    }

    public static UpdateBoardDto from(Board board) {
        return new UpdateBoardDto(board);
    }
}
