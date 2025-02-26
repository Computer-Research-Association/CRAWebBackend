package com.handong.cra.crawebbackend.item.dto.response;

import com.handong.cra.crawebbackend.board.dto.PageBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResListBoardDto;
import com.handong.cra.crawebbackend.board.dto.response.ResPageBoardDto;
import com.handong.cra.crawebbackend.item.dto.PageItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResPageItemDto {
    private List<ResListItemDto> resListItemDtos;
    private Integer totalPages;


    public static ResPageItemDto from(PageItemDto pageItemDto) {
        return new ResPageItemDto(pageItemDto.getListItemDtos().stream().map(ResListItemDto::from).toList(), pageItemDto.getTotalPages());
    }
}
