package com.handong.cra.crawebbackend.item.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.item.domain.Item;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.item.dto.CreateItemDto;
import com.handong.cra.crawebbackend.item.dto.DetailItemDto;
import com.handong.cra.crawebbackend.item.dto.ListItemDto;
import com.handong.cra.crawebbackend.item.dto.UpdateItemDto;
import com.handong.cra.crawebbackend.item.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        item = itemRepository.save(item);
        return CreateItemDto.from(item);
    }

    @Override
    @Transactional
    public UpdateItemDto updateItem(Long id, UpdateItemDto updateItemDto) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("no data"));
        item = item.update(updateItemDto);
        return UpdateItemDto.from(item);
    }

    @Override
    @Transactional
    public Boolean deleteItemById(Long id) {
        itemRepository.findById(id).orElseThrow(() -> new RuntimeException("no data")).delete();
        return true;
    }

    @Override
    @Transactional
    public Boolean changeValidatingById(Long id, Boolean valid) {
        itemRepository.findById(id).orElseThrow(() -> new RuntimeException("no data")).setIsBorrowed(valid);
        return true;
    }

    @Override
    public List<ListItemDto> getPaginationItem(Long page, Integer perPage, Boolean isASC, ItemCategory itemCategory) {
        Sort sort = Sort.by("createdAt");

        sort = (isASC) ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(Math.toIntExact(page), perPage, sort);
        Page<Item> pages = itemRepository.findAllByItemCategoryAndDeletedFalse(itemCategory, pageable);

        return pages.stream().map(ListItemDto::from).toList();
    }


    @Override
    public List<ListItemDto> getItemsByCategory(ItemCategory itemCategory) {
        List<Item> items = itemRepository.findAllByItemCategoryAndDeletedFalse(itemCategory);
        return items.stream().map(ListItemDto::from).toList();
    }


    @Override
    public DetailItemDto getDetailById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("no data"));
        if (item.getDeleted()) return null;
        else return DetailItemDto.from(item);
    }

}
