package com.handong.cra.crawebbackend.board.dto;

import com.handong.cra.crawebbackend.board.domain.BoardPin;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.request.ReqBoardPinDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class BoardPinDto {
    private Long Id;
    private Long boardId;
    private Category category;
    private LocalDateTime pinedAt;

    public static BoardPinDto from(final ReqBoardPinDto reqBoardPinDto) {
        return builder()
                .boardId(reqBoardPinDto.getBoardId())
                .category(Category.values()[reqBoardPinDto.getCategory()])
                .build();
    }

    public static BoardPinDto from(final BoardPinDto BoardPinDto) {
        return builder()
                .Id(BoardPinDto.getId())
                .boardId(BoardPinDto.getBoardId())
                .category(BoardPinDto.getCategory())
                .pinedAt(BoardPinDto.getPinedAt())
                .build();
    }

    public static BoardPinDto from(BoardPin newBoardPin) {
        return builder()
                .Id(newBoardPin.getId())
                .boardId(newBoardPin.getBoard().getId())
                .category(newBoardPin.getCategory())
                .pinedAt(newBoardPin.getCreatedAt())
                .build();
    }
}
