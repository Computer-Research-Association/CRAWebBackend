package com.handong.cra.crawebbackend.gallery.domain;


import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gallery extends BaseEntity {
    @Column(nullable = false, name = "img_url")
    private String imgUrl;

}