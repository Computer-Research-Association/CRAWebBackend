package com.handong.cra.crawebbackend.tag.dto;

import com.handong.cra.crawebbackend.tag.domain.Tag;
import com.handong.cra.crawebbackend.tag.dto.request.ReqCreateTagDto;
import lombok.*;

@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
public class TagDto {

    private Long id;
    private String name;

    public static TagDto from(final ReqCreateTagDto reqCreateTagDto) {
        return builder()
                .name(reqCreateTagDto.getName())
                .build();
    }

    public static TagDto from(final Tag tag) {
        return builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}