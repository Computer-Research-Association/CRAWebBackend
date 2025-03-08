package com.handong.cra.crawebbackend.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class SearchPageBoardDto {
    private List<ListBoardDto> listBoardDtos;
    private Integer totalPages;
    private Integer totalBoards;

}
