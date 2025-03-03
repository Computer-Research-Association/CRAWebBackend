package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.dto.PageBoardDto;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
public class ResPageBoardDto {
    private List<ResListBoardDto> resListBoardDtos;
    private List<ResBoardPinDto> resBoardPinDtos;
    private Integer totalPages;

    public static ResPageBoardDto from(PageBoardDto pageBoardDto) {
        return builder()
                .resListBoardDtos((pageBoardDto.getListBoardDtos() != null) ? pageBoardDto.getListBoardDtos().stream().map(ResListBoardDto::from).toList() : List.of())
                .resBoardPinDtos((pageBoardDto.getBoardPinDtos() != null) ? pageBoardDto.getBoardPinDtos().stream().map(ResBoardPinDto::from).toList() : List.of())
                .totalPages(pageBoardDto.getTotalPages())
                .build();
    }
}
