package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.dto.PageBoardDto;
import com.handong.cra.crawebbackend.board.dto.SearchPageBoardDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ResSearchPageBoardDto {
    private List<ResListBoardDto> resListBoardDtos;
    private Integer totalPages;
    private Integer totalBoards;

    public static ResSearchPageBoardDto from(SearchPageBoardDto searchPageBoardDto) {
        return builder()
                .resListBoardDtos((searchPageBoardDto.getListBoardDtos() != null) ? searchPageBoardDto.getListBoardDtos().stream().map(ResListBoardDto::from).toList() : List.of())
                .totalPages(searchPageBoardDto.getTotalPages())
                .totalBoards(searchPageBoardDto.getTotalBoards())
                .build();
    }
}
