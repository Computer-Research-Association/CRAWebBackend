package com.handong.cra.crawebbackend.tag.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

//그냥 create Dto만들어서 내부적으로
public class ReqCreateTagDto {
    private String name;
}