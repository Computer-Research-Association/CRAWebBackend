package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.HavrutaDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "Board 생성 완료 데이터 전달 DTO")
public class ResCreateBoardDto {
    @Schema(description = "글 제목")
    private Long id;

    @Schema(description = "작성자 id")
    private Long userId;

    @Schema(description = "글 제목")
    private String title;

    @Schema(description = "글 내용")
    private String content;

    @Schema(description = "글 카테고리")
    private Category category;

    @Schema(description = "사진 주소 목록")
    private List<String> imageUrls;

    @Schema(description = "생성 시간")
    private LocalDateTime createdAt;


    private HavrutaDto havrutaDto;

    public ResCreateBoardDto(CreateBoardDto createBoardDto) {

        this.id = createBoardDto.getId();
        this.userId = createBoardDto.getUserId();
        this.title = createBoardDto.getTitle();
        this.content = createBoardDto.getContent();
        this.category = createBoardDto.getCategory();
        this.imageUrls = createBoardDto.getImageUrls();
        this.createdAt = createBoardDto.getCreatedAt();

        if (createBoardDto.getHavrutaDto() != null) {
            this.havrutaDto.setId(createBoardDto.getHavrutaDto().getId());
            this.havrutaDto.setClassname(createBoardDto.getHavrutaDto().getClassname());
            this.havrutaDto.setProfessor(createBoardDto.getHavrutaDto().getProfessor());
        }
    }

    public static ResCreateBoardDto from(CreateBoardDto createBoardDto) {
        return new ResCreateBoardDto(createBoardDto);
    }

}
