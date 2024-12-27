package com.handong.cra.crawebbackend.item.service;

import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.item.dto.ListItemDto;

import java.util.List;

public interface ItemService {
    public CreateItemDto createItem(CreateItemDto createItemDto);
    public List<ListItemDto> getItemList();
}
