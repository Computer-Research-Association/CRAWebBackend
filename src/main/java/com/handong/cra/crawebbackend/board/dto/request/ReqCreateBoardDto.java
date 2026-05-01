package com.handong.cra.crawebbackend.board.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqCreateBoardDto {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Integer category;

    private List<String> imageUrls;

    private List<String> tags;

}
