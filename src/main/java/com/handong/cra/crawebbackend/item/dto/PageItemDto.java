package com.handong.cra.crawebbackend.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageItemDto {
    private List<ListItemDto> listItemDtos;
    private Integer totalPages;


    public static PageItemDto of(List<ListItemDto> listItemDtos, Integer totalPages) {
        return new PageItemDto(listItemDtos, totalPages);
    }
}
