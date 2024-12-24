package com.handong.cra.crawebbackend.board.dto;

import com.handong.cra.crawebbackend.board.domain.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqCreateBoardDto {
    private Long userId;
    private String title;
    private String content;
    private Category category;
    private List<String> imageUrls;
    // TODO: add havrutaid
}
