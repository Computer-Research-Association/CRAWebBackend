package com.handong.cra.crawebbackend.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqUpdateBoardDto {
    private String title;
    private String content;
    private List<String> imageUrls;
    private Boolean isChangedFile;
    private Boolean deleted;

}
