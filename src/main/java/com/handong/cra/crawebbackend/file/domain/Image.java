package com.handong.cra.crawebbackend.file.domain;

import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 2048)
    private String imgUrl;

    @NotBlank
    @Column(nullable = false, length = 2048)
    private String key;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ImageCategory imageCategory;

    @Setter
    @Column(nullable = false)
    private Boolean isUsing = false; // 사용중인지? -> 등록이후 사용 설정. 설정하지 않으면 cron bot 이 주기적으로 삭제 진행.


    public void setUsing(Boolean using) {
        isUsing = using;
    }



}
