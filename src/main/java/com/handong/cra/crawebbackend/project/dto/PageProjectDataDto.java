package com.handong.cra.crawebbackend.project.dto;

import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.project.domain.ProjectOrderBy;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PageProjectDataDto {
    private Long page;
    private Integer perPage;
    private Boolean isASC;
    private ProjectOrderBy projectOrderBy;
}
