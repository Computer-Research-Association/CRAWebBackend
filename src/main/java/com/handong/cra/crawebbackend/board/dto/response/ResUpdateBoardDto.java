package com.handong.cra.crawebbackend.board.dto.response;


import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.tag.dto.response.ResTagDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResUpdateBoardDto {
    private Long id;
    private Boolean deleted;
    private String title;
    private String content;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ResUserDetailDto resUserDetailDto;
    private List<ResTagDto> tags;

    public ResUpdateBoardDto(UpdateBoardDto updateBoardDto) {
        this.id = updateBoardDto.getId();
        this.deleted = updateBoardDto.getDeleted();
        this.title = updateBoardDto.getTitle();
        this.content = updateBoardDto.getContent();
        this.resUserDetailDto = ResUserDetailDto.from(updateBoardDto.getUserDetailDto());
        this.imageUrls = updateBoardDto.getImageUrls();
        this.createdAt = updateBoardDto.getCreatedAt();
        this.updatedAt = updateBoardDto.getUpdatedAt();
        this.tags = updateBoardDto.getTags();
    }

    public static ResUpdateBoardDto from(UpdateBoardDto updateBoardDto) {
        return new ResUpdateBoardDto(updateBoardDto);
    }
}
