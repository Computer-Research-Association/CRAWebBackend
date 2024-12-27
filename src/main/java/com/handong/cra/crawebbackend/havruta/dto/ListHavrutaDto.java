package com.handong.cra.crawebbackend.havruta.dto;

import com.handong.cra.crawebbackend.havruta.domain.Havruta;

public class ListHavrutaDto {
    private Long id;
    private String className;

    public static ListHavrutaDto of(Havruta havruta) {
        this.id = havruta.getId();
        this.className = havruta.getClassName();
    }
}
