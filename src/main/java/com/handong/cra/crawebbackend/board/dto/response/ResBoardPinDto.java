package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.BoardPinDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ResBoardPinDto {
    private Long id;
    private Long boardId;
    private Category category;
    private LocalDateTime pinedAt;

    public static ResBoardPinDto from(final BoardPinDto boardPinDto) {
        return builder()
                .id(boardPinDto.getId())
                .boardId(boardPinDto.getBoardId())
                .category(boardPinDto.getCategory())
                .pinedAt(boardPinDto.getPinedAt())
                .build();
    }
}
