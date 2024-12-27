package com.handong.cra.crawebbackend.item.service;

import com.handong.cra.crawebbackend.item.domain.Item;
import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.item.dto.ListItemDto;
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
    @Transactional
    public List<ListItemDto> getItemList() {
        List<Item> items = itemRepository.findAll();
        List<ListItemDto> itemDtos = new ArrayList<>();
        for (Item item : items) {
            itemDtos.add(ListItemDto.from(item));
        }

        return itemDtos;
    }
}
