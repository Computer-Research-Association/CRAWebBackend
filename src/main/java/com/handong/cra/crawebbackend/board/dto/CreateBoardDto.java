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
    private List<MultipartFile> files;
    private List<String> fileUrls;
    private LocalDateTime createdAt;

    private HavrutaDto havrutaDto;

    public CreateBoardDto(Long userId, ReqCreateBoardDto reqCreateBoardDto, List<MultipartFile> files) {
        this.userId = userId;
        this.title = reqCreateBoardDto.getTitle();
        this.content = reqCreateBoardDto.getContent();
        this.category = Category.values()[reqCreateBoardDto.getCategory()];
        this.imageUrls = reqCreateBoardDto.getImageUrls();
        this.havrutaDto = reqCreateBoardDto.getHavrutaDto();
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
        this.havrutaDto.setId(board.getHavruta().getId());
        this.havrutaDto.setProfessor(board.getHavruta().getProfessor());
        this.havrutaDto.setClassname(board.getHavruta().getClassname());
    }

//    public CreateBoardDto(Long userId, CreateHavrutaBoardDto createHavrutaBoardDto) {
//        this.userId = userId;
//        this.title = createHavrutaBoardDto.getTitle();
//        this.content = createHavrutaBoardDto.getContent();
//        this.category = createHavrutaBoardDto.getCategory();
//        this.imageUrls = createHavrutaBoardDto.getImageUrls();
//        this.fileUrls = createHavrutaBoardDto.getFileUrls();
//        this.havrutaDto.setId(board.getHavruta().getId());
//        this.havrutaDto.setProfessor(board.getHavruta().getProfessor());
//        this.havrutaDto.setClassname(board.getHavruta().getClassName());
//    }
//
//    public CreateBoardDto(Long userId, ReqCreateHavrutaBoardDto reqCreateHavrutaBoardDto) {
//        this.userId = userId;
//        this.title = reqCreateHavrutaBoardDto.getTitle();
//        this.content = reqCreateHavrutaBoardDto.getContent();
//        this.category = Category.values()[reqCreateHavrutaBoardDto.getCategory()];
//        this.imageUrls = reqCreateHavrutaBoardDto.getImageUrls();
//        this.havrutaDto.setId(reqCreateHavrutaBoardDto.getHavrutaDto().getId());
//        this.havrutaDto.setProfessor(reqCreateHavrutaBoardDto.getHavrutaDto().getProfessor());
//        this.havrutaDto.setClassname(reqCreateHavrutaBoardDto.getHavrutaDto().getClassname());
//
//    }

    // TODO : user logic
    public static CreateBoardDto of(Long userId, ReqCreateBoardDto reqCreateBoardDto, List<MultipartFile> files) {
        return new CreateBoardDto(userId, reqCreateBoardDto, files);
    }

    public static CreateBoardDto from(Board board) {
        return new CreateBoardDto(board);
    }
//
//    public static CreateBoardDto from(Long userId, CreateHavrutaBoardDto createHavrutaBoardDto) {
//        return new CreateBoardDto(userId, createHavrutaBoardDto);
//    }
}
