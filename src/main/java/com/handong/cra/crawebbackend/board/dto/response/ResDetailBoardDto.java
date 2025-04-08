package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.DetailBoardDto;
import com.handong.cra.crawebbackend.comment.dto.response.ResListCommentDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResDetailBoardDto {
    private Long id;
    private String title;
    private String content;
    private Category category;
    private Long likeCount;
    private Long view;
    private List<String> imageUrls;
    private String fileUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ResUserDetailDto resUserDetailDto;
    private Boolean viewerLiked;
    private Long pidId;
    private Boolean isPined;
    private List<ResListCommentDto> resListCommentDtos;

    public ResDetailBoardDto(DetailBoardDto detailBoardDto) {
        this.id = detailBoardDto.getId();
        this.title = detailBoardDto.getTitle();
        this.content = detailBoardDto.getContent();
        this.category = detailBoardDto.getCategory();
        this.likeCount = detailBoardDto.getLikeCount();
        this.view = detailBoardDto.getView();
        this.fileUrl = detailBoardDto.getFileUrl();
        this.createdAt = detailBoardDto.getCreatedAt();
        this.updatedAt = detailBoardDto.getUpdatedAt();
        this.imageUrls = detailBoardDto.getImageUrls();
        this.pidId = detailBoardDto.getPinId();
        this.isPined = detailBoardDto.getIsPined();
        this.resUserDetailDto = ResUserDetailDto.from(detailBoardDto.getUserDetailDto());
        this.viewerLiked = detailBoardDto.getViewerLiked();
        if (detailBoardDto.getListCommentDtos() != null)
            this.resListCommentDtos = detailBoardDto.getListCommentDtos().stream().map(ResListCommentDto::from).toList();
    }

    public static ResDetailBoardDto from(DetailBoardDto detailBoardDto) {
        return new ResDetailBoardDto(detailBoardDto);
    }
}
