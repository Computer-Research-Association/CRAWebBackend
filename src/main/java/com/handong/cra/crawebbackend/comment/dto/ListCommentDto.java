package com.handong.cra.crawebbackend.comment.dto;

import com.handong.cra.crawebbackend.comment.domain.Comment;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ListCommentDto {
    private Long id;
    private List<ListCommentDto> commentDtoList;
    private Long userId;
    private Long boardId;
    private UserDetailDto userDetailDto;
    private String content;
    private Long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted;

    public ListCommentDto(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.boardId = comment.getBoard().getId();
        this.userDetailDto = UserDetailDto.from(comment.getUser());
        this.commentDtoList = comment.getCommentList().stream().map(ListCommentDto::from).toList();
        this.content = comment.getContent();
        this.likeCount = comment.getLikeCount();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.deleted = comment.getDeleted();
    }

    public static ListCommentDto from(Comment comment) {
        return new ListCommentDto(comment);
    }
}
