package com.handong.cra.crawebbackend.board.dto;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.domain.Category;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PageBoardDataDto {
    private Category category;
    private Long page;
    private Integer perPage;
    private BoardOrderBy orderBy;
    private Boolean isASC;
}
