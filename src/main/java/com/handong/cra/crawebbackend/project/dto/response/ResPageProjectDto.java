package com.handong.cra.crawebbackend.project.dto.response;

import com.handong.cra.crawebbackend.project.dto.PageProjectDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResPageProjectDto {
    private List<ResListProjectDto> resListProjectDtos;
    private Integer totalPages;


    public static ResPageProjectDto from(PageProjectDto pageProjectDto) {
        return new ResPageProjectDto(pageProjectDto.getListProjectDtos().stream().map(ResListProjectDto::from).toList(), pageProjectDto.getTotalPages());
    }
}
