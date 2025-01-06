package com.handong.cra.crawebbackend.comment.dto.response;

import com.handong.cra.crawebbackend.comment.dto.CreateCommentDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResCreateCommentDto {
    private Long id;
    private Long userId;
    private Long parentCommentId;
    private Long boardId;
    private String content;
    private LocalDateTime createdAt;

    public ResCreateCommentDto(CreateCommentDto createCommentDto) {
        this.id = createCommentDto.getId();
        this.userId = createCommentDto.getUserId();
        this.boardId = createCommentDto.getBoardId();
        this.content = createCommentDto.getContent();
        this.createdAt = createCommentDto.getCreatedAt();
    }

    public static  ResCreateCommentDto from(CreateCommentDto createCommentDto){
        return new ResCreateCommentDto(createCommentDto);
    }
}
