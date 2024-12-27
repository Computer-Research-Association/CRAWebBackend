package com.handong.cra.crawebbackend.item.dto.response;

import com.handong.cra.crawebbackend.item.domain.Item;
import com.handong.cra.crawebbackend.item.dto.ListItemDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResListItemDto {
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

    public static ResListItemDto from(ListItemDto listItemDto) {
        return ResListItemDto.builder()
                .id(listItemDto.getId())
                .userId(listItemDto.getUserId())
                .name(listItemDto.getName())
                .description(listItemDto.getDescription())
                .imageUrl(listItemDto.getImageUrl())
                .createdAt(listItemDto.getCreatedAt())
                .updatedAt(listItemDto.getUpdatedAt())
                .borrowDate(listItemDto.getBorrowDate())
                .returnDate(listItemDto.getReturnDate())
                .dueDate(listItemDto.getDueDate())
                .build();
    }
}
