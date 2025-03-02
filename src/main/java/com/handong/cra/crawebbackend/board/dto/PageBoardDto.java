package com.handong.cra.crawebbackend.board.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class PageBoardDto {
    private List<ListBoardDto> listBoardDtos;
    private List<BoardPinDto> boardPinDtos;
    private Integer totalPages;
}
