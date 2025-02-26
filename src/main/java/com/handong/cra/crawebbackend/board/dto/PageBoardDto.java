package com.handong.cra.crawebbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageBoardDto {
    private List<ListBoardDto> ListBoardDtoList;
    private Integer totalPages;




}
