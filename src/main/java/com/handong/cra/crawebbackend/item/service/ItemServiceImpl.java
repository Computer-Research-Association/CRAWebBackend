package com.handong.cra.crawebbackend.item.service;

import com.handong.cra.crawebbackend.item.domain.Item;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.item.dto.DetailItemDto;
import com.handong.cra.crawebbackend.item.dto.ListItemDto;
import com.handong.cra.crawebbackend.item.dto.UpdateItemDto;
import com.handong.cra.crawebbackend.item.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public CreateItemDto createItem(CreateItemDto createItemDto) {
        Item item = Item.from(createItemDto);
        itemRepository.save(item);
        return CreateItemDto
                .builder()
                .id(item.getId())
                .name(item.getName())
                .imageUrl(item.getImageUrl())
                .createdAt(item.getCreatedAt())
                .build();
    }

    @Override
    public UpdateItemDto updateItem(UpdateItemDto updateItemDto) {
        return null;
    }

    @Override
    public Boolean deleteItemById(Long id) {
        return null;
    }

    @Override
    public Boolean setBorrowedById(Long id) {
        return null;
    }

    @Override
    public Boolean setReturnedById(Long id) {
        return null;
    }

    @Override
    public List<ListItemDto> getItemsByCategory(ItemCategory itemCategory) {
        return List.of();
    }

    @Override
    public DetailItemDto getDetailById(Long id) {
        return null;
    }

}
