package com.handong.cra.crawebbackend.tag.dto.response;

import com.handong.cra.crawebbackend.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
public class ResTagDto {

    private Long id;
    private String name;

    public static ResTagDto from(final Tag tag) {
        return builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}