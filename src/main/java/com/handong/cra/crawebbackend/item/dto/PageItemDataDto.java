package com.handong.cra.crawebbackend.item.dto;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PageItemDataDto {
    private Long page;
    private Integer perPage;
    private Boolean isASC;
    private ItemCategory itemCategory;
}
