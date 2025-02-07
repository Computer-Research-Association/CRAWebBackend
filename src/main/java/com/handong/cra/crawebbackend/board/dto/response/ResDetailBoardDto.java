package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.DetailBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.HavrutaDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "Board 정보 데이터 전달 DTO")
public class ResDetailBoardDto {
    @Schema(description = "글 id")
    private Long id;

    @Schema(description = "작성자 id")
    private Long userId;

    @Schema(description = "글 제목")
    private String title;

    @Schema(description = "글 내용")
    private String content;

    @Schema(description = "글 카테고리")
    private Category category;

    @Schema(description = "글 좋아요 수")
    private Long likeCount;

    @Schema(description = "글 조회수")
    private Long view;

    @Schema(description = "사진 주소 목록")
    private List<String> imageUrls;

    @Schema(description = "파일 주소 목록")
    private List<String> fileUrls;

    @Schema(description = "글 생성 시간")
    private LocalDateTime createdAt;

    @Schema(description = "글 수정 시간")
    private LocalDateTime updatedAt;

    private HavrutaDto havrutaDto;

    public ResDetailBoardDto(DetailBoardDto detailBoardDto) {
        this.id = detailBoardDto.getId();
        this.userId = detailBoardDto.getUserId();
        this.title = detailBoardDto.getTitle();
        this.content = detailBoardDto.getContent();
        this.category = detailBoardDto.getCategory();
        this.likeCount = detailBoardDto.getLikeCount();
        this.view = detailBoardDto.getView();
        this.imageUrls = detailBoardDto.getImageUrls();
        this.fileUrls = detailBoardDto.getFileUrls();
        this.createdAt = detailBoardDto.getCreatedAt();
        this.updatedAt = detailBoardDto.getUpdatedAt();

        if (detailBoardDto.getHavrutaDto() != null) {
            this.havrutaDto.setId(detailBoardDto.getHavrutaDto().getId());
            this.havrutaDto.setClassname(detailBoardDto.getHavrutaDto().getClassname());
            this.havrutaDto.setProfessor(detailBoardDto.getHavrutaDto().getProfessor());
        }
    }


    public static ResDetailBoardDto from(DetailBoardDto detailBoardDto) {
        return new ResDetailBoardDto(detailBoardDto);
    }
}
