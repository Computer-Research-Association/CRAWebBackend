package com.handong.cra.crawebbackend.item.service;

import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.item.dto.DetailItemDto;
import com.handong.cra.crawebbackend.item.dto.ListItemDto;
import com.handong.cra.crawebbackend.item.dto.UpdateItemDto;

import java.util.List;

public interface ItemService {
    public CreateItemDto createItem(CreateItemDto createItemDto);
    public UpdateItemDto updateItem(UpdateItemDto updateItemDto);
    public Boolean deleteItemById(Long id);

    public Boolean setBorrowedById(Long id); // 대여
    public Boolean setReturnedById(Long id); // 반납

    public List<ListItemDto> getItemsByCategory(ItemCategory itemCategory);
    public DetailItemDto getDetailById(Long id);


}
