package com.handong.cra.crawebbackend.board.dto.response;

import com.handong.cra.crawebbackend.board.dto.PageBoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResPageBoardDto {
    private List<ResListBoardDto> resListBoardDtoList;
    private Integer totalPages;

    public static ResPageBoardDto from(PageBoardDto pageBoardDto) {
        return new ResPageBoardDto(
                pageBoardDto.getListBoardDtoList().stream().map(ResListBoardDto::from).toList(),
                pageBoardDto.getTotalPages());
    }
}
