package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.CreateBoardDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResCreateBoardDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Category category;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    // TODO: add havrutaid


    public ResCreateBoardDto(CreateBoardDto createBoardDto) {

        this.id = createBoardDto.getId();
        this.userId = createBoardDto.getUserId();
        this.title = createBoardDto.getTitle();
        this.content = createBoardDto.getContent();
        this.category = createBoardDto.getCategory();
        this.imageUrls = createBoardDto.getImageUrls();
        this.createdAt= createBoardDto.getCreatedAt();
    }

    public static ResCreateBoardDto from(CreateBoardDto createBoardDto) {
            return new ResCreateBoardDto(createBoardDto);
    }

}
