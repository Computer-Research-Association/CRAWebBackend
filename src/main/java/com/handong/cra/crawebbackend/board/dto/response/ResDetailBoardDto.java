package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.DetailBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.HavrutaDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
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
    private String fileUrl;

    @Schema(description = "글 생성 시간")
    private LocalDateTime createdAt;

    @Schema(description = "글 수정 시간")
    private LocalDateTime updatedAt;

    private ResUserDetailDto resUserDetailDto;

    private HavrutaDto havrutaDto;

    private Boolean viewerLiked;

    public ResDetailBoardDto(DetailBoardDto detailBoardDto) {
        this.id = detailBoardDto.getId();
        this.title = detailBoardDto.getTitle();
        this.content = detailBoardDto.getContent();
        this.category = detailBoardDto.getCategory();
        this.likeCount = detailBoardDto.getLikeCount();
        this.view = detailBoardDto.getView();
        this.imageUrls = detailBoardDto.getImageUrls();
        this.fileUrl = detailBoardDto.getFileUrl();
        this.createdAt = detailBoardDto.getCreatedAt();
        this.updatedAt = detailBoardDto.getUpdatedAt();

        if (detailBoardDto.getHavrutaDto() != null) {
            this.havrutaDto = detailBoardDto.getHavrutaDto();
        }
        this.resUserDetailDto = ResUserDetailDto.from(detailBoardDto.getUserDetailDto());

        // nullable
        this.viewerLiked = detailBoardDto.getViewerLiked();
    }


    public static ResDetailBoardDto from(DetailBoardDto detailBoardDto) {
        return new ResDetailBoardDto(detailBoardDto);
    }
}
