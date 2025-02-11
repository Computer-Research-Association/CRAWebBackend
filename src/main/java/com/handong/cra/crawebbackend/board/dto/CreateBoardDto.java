package com.handong.cra.crawebbackend.board.dto;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.request.ReqCreateBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.HavrutaDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateBoardDto {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Category category;
    private List<String> imageUrls;
    private MultipartFile file;
    private String fileUrl;
    private LocalDateTime createdAt;

    private HavrutaDto havrutaDto;

    public CreateBoardDto(Long userId, ReqCreateBoardDto reqCreateBoardDto, MultipartFile file) {
        this.userId = userId;
        this.title = reqCreateBoardDto.getTitle();
        this.content = reqCreateBoardDto.getContent();
        this.category = Category.values()[reqCreateBoardDto.getCategory()];
        this.imageUrls = reqCreateBoardDto.getImageUrls();

        if (reqCreateBoardDto.getHavrutaDto() != null)
            this.havrutaDto = reqCreateBoardDto.getHavrutaDto();
        this.file = file;
    }

    public CreateBoardDto(Board board) {
        this.id = board.getId();
        this.userId = board.getUser().getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.imageUrls = board.getImageUrls();
        this.createdAt = board.getCreatedAt();
        this.fileUrl = board.getFileUrl();
        if (board.getHavruta() != null) {
            this.havrutaDto = new HavrutaDto();
            this.havrutaDto.setId(board.getHavruta().getId());
            this.havrutaDto.setProfessor(board.getHavruta().getProfessor());
            this.havrutaDto.setClassname(board.getHavruta().getClassname());
        }
    }

    public static CreateBoardDto of(Long userId, ReqCreateBoardDto reqCreateBoardDto, MultipartFile file) {
        return new CreateBoardDto(userId, reqCreateBoardDto, file);
    }

    public static CreateBoardDto from(Board board) {
        return new CreateBoardDto(board);
    }
}
