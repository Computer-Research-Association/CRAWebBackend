package com.handong.cra.crawebbackend.user.dto;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.domain.Category;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PageUserDataDto {
    private Long page;
    private Integer perPage;
}
