package com.handong.cra.crawebbackend.item.service;

import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.item.dto.*;

import java.util.List;

public interface ItemService {
    public CreateItemDto createItem(CreateItemDto createItemDto);

    public UpdateItemDto updateItem(UpdateItemDto updateItemDto);

    public Boolean deleteItemById(UpdateItemDto updateItemDto);

    public Boolean changeValidatingById(UpdateItemDto updateItemDto); // 대여

    public PageItemDto getPaginationItem(PageItemDataDto pageItemDataDto);

    public List<ListItemDto> getItemsByCategory(ItemCategory itemCategory);

    public DetailItemDto getDetailById(Long id);


}
