package com.handong.cra.crawebbackend.item.domain;

import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 2048, nullable = false)
    private String description;

    @Column(length = 256)
    private String imageUrl;

    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private LocalDateTime dueDate;

    public Item(CreateItemDto createItemDto) {
        this.name = createItemDto.getName();
        this.imageUrl = createItemDto.getImageUrl();
    }

    public static Item from(CreateItemDto createItemDto) {
        return new Item(createItemDto);
    }
}
