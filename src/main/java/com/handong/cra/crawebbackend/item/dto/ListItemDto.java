package com.handong.cra.crawebbackend.item.dto;

import com.handong.cra.crawebbackend.item.domain.Item;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ListItemDto {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private LocalDateTime dueDate;

    public static ListItemDto from(Item item) {
        return ListItemDto.builder()
                .id(item.getId())
                .userId(item.getUser().getId())
                .name(item.getName())
                .description(item.getDescription())
                .imageUrl(item.getImageUrl())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .borrowDate(item.getBorrowDate())
                .returnDate(item.getReturnDate())
                .dueDate(item.getDueDate())
                .build();
    }
}
