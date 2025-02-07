package com.handong.cra.crawebbackend.item.service;

import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.item.dto.DetailItemDto;
import com.handong.cra.crawebbackend.item.dto.ListItemDto;
import com.handong.cra.crawebbackend.item.dto.UpdateItemDto;

import java.util.List;

public interface ItemService {
    public CreateItemDto createItem(CreateItemDto createItemDto);

    public UpdateItemDto updateItem(Long id, UpdateItemDto updateItemDto);

    public Boolean deleteItemById(UpdateItemDto updateItemDto);

    public Boolean changeValidatingById(UpdateItemDto updateItemDto); // 대여

    public List<ListItemDto> getPaginationItem(Long page, Integer perPage, Boolean isASC, ItemCategory itemCategory);

    public List<ListItemDto> getItemsByCategory(ItemCategory itemCategory);

    public DetailItemDto getDetailById(Long id);


}
