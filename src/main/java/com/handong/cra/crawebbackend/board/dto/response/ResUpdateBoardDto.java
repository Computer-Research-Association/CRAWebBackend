package com.handong.cra.crawebbackend.board.dto.response;


import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "Board 수정 정보 데이터 전달 DTO")
public class ResUpdateBoardDto {

    @Schema(description = "글 id")
    private Long id;

    @Schema(description = "글 삭제 여부")
    private Boolean deleted;

    @Schema(description = "글 제목")
    private String title;

    @Schema(description = "글 내용")
    private String content;

    @Schema(description = "사진 주소 목록")
    private List<String> imageUrls;

    @Schema(description = "글 작성 시간")
    private LocalDateTime createdAt;

    @Schema(description = "글 수정 시간")
    private LocalDateTime updatedAt;

    private ResUserDetailDto resUserDetailDto;

    public ResUpdateBoardDto(UpdateBoardDto updateBoardDto) {
        this.id = updateBoardDto.getId();
        this.userId = updateBoardDto.getUserId();
        this.deleted = updateBoardDto.getDeleted();
        this.title = updateBoardDto.getTitle();
        this.content = updateBoardDto.getContent();
        this.resUserDetailDto = ResUserDetailDto.from(updateBoardDto.getUserDetailDto());
        this.imageUrls = updateBoardDto.getImageUrls();
        this.createdAt = updateBoardDto.getCreatedAt();
        this.updatedAt = updateBoardDto.getUpdatedAt();

    }

    public static ResUpdateBoardDto from(UpdateBoardDto updateBoardDto) {
        return new ResUpdateBoardDto(updateBoardDto);
    }
}
