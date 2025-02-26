package com.handong.cra.crawebbackend.project.dto;

import com.handong.cra.crawebbackend.item.dto.response.ResListItemDto;
import com.handong.cra.crawebbackend.project.dto.response.ResListProjectDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageProjectDto {
    private List<ListProjectDto> ListProjectDtos;
    private Integer totalPages;


    public static PageProjectDto of(List<ListProjectDto> resListProjectDtos, Integer totalPages) {
        return new PageProjectDto(resListProjectDtos, totalPages);
    }
}
