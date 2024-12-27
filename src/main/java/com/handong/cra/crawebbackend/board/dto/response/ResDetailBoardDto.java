package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.DetailBoardDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResDetailBoardDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Integer category;
    private Long likeCount;
    private Long view;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // TODO: add havrutaid


    public static ResDetailBoardDto from(DetailBoardDto detailBoardDto) {
        if (detailBoardDto == null) return null;
        return ResDetailBoardDto.builder()
                .id(detailBoardDto.getId())
                .userId(detailBoardDto.getUserId())
                .title(detailBoardDto.getTitle())
                .content(detailBoardDto.getContent())
                .category(detailBoardDto.getCategory().ordinal())
                .likeCount(detailBoardDto.getLikeCount())
                .view(detailBoardDto.getView())
                .imageUrls(detailBoardDto.getImageUrls())
                .createdAt(detailBoardDto.getCreatedAt())
                .updatedAt(detailBoardDto.getUpdatedAt())
                .build();
    }
}
