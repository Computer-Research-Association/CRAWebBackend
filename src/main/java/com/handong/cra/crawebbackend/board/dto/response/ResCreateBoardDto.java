package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
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
public class ResCreateBoardDto {
    private Long id;
    private String title;
    private String content;
    private Category category;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    private ResUserDetailDto resUserDetailDto;
    private List<ResTagDto> tags;

    public ResCreateBoardDto(CreateBoardDto createBoardDto) {
        this.id = createBoardDto.getId();
        this.title = createBoardDto.getTitle();
        this.content = createBoardDto.getContent();
        this.category = createBoardDto.getCategory();
        this.imageUrls = createBoardDto.getImageUrls();
        this.createdAt = createBoardDto.getCreatedAt();
        this.resUserDetailDto = ResUserDetailDto.from(createBoardDto.getUserDetailDto());
        this.tags = createBoardDto.getTags();

    }

    public static ResCreateBoardDto from(CreateBoardDto createBoardDto) {
        return new ResCreateBoardDto(createBoardDto);
    }

}
