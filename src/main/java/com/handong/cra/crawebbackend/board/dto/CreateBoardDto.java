package com.handong.cra.crawebbackend.board.dto;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.CreateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.request.ReqCreateHavrutaBoardDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateBoardDto {

    private Long userId;
    private String title;
    private String content;
    private Category category; //= Category.valueOf("HAVRUTA");
    private List<String> imageUrls;
    private List<MultipartFile> files;
    private List<String> fileUrls;

    private Long id = null;
    private LocalDateTime createdAt = null;


    public CreateBoardDto(ReqCreateBoardDto reqCreateBoardDto, Long userId, List<MultipartFile> files) {
        this.userId = userId;
        this.title = reqCreateBoardDto.getTitle();
        this.content = reqCreateBoardDto.getContent();
        this.category = Category.values()[reqCreateBoardDto.getCategory()];
        this.imageUrls = reqCreateBoardDto.getImageUrls();
        this.files = files;
    }

    public CreateBoardDto(Board board) {
        this.id = board.getId();
        this.userId = board.getUser().getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.imageUrls = board.getImageUrls();
        this.createdAt = board.getCreatedAt();
        this.fileUrls = board.getFileUrls();
    }

    public CreateBoardDto(CreateHavrutaBoardDto createHavrutaBoardDto, Long userId) {
        this.userId = userId;
        this.title = createHavrutaBoardDto.getTitle();
        this.content = createHavrutaBoardDto.getContent();
        this.category = createHavrutaBoardDto.getCategory();
        this.imageUrls = createHavrutaBoardDto.getImageUrls();
        this.fileUrls = createHavrutaBoardDto.getFileUrls();
    }

    public CreateBoardDto(ReqCreateHavrutaBoardDto reqCreateHavrutaBoardDto) {
        this.userId = reqCreateHavrutaBoardDto.getUserId();
        this.title = reqCreateHavrutaBoardDto.getTitle();
        this.content = reqCreateHavrutaBoardDto.getContent();
        this.category = Category.values()[reqCreateHavrutaBoardDto.getCategory()];
        this.imageUrls = reqCreateHavrutaBoardDto.getImageUrls();

    }

    // TODO : user logic
    public static CreateBoardDto of(ReqCreateBoardDto reqCreateBoardDto, Long userId, List<MultipartFile> files) {
        return new CreateBoardDto(reqCreateBoardDto, userId, files);
    }

    public static CreateBoardDto from(Board board) {
        return new CreateBoardDto(board);
    }
    public static CreateBoardDto from(CreateHavrutaBoardDto createHavrutaBoardDto, Long userId) {
        return new CreateBoardDto(createHavrutaBoardDto, userId);
    }
}
