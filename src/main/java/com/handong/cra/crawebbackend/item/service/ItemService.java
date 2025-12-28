package com.handong.cra.crawebbackend.item.service;

import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import com.handong.cra.crawebbackend.item.dto.*;

import java.util.List;

public interface ItemService {

    /**
     * 아이템 생성
     *
     * @param createItemDto 아이템 데이터 DTO
     */
    CreateItemDto createItem(CreateItemDto createItemDto);

    /**
     * 아이템 수정
     *
     * @param updateItemDto 아이템 수정 데이터 DTO
     */
    UpdateItemDto updateItem(UpdateItemDto updateItemDto);

    /**
     * 아이템 삭제
     *
     * @param updateItemDto 아이템 삭제 데이터 DTO
     */
    Boolean deleteItemById(UpdateItemDto updateItemDto);

    /**
     * 아이템 대여, 반납 (도서)
     *
     * @param updateItemDto 대여할 아이템 DTO
     */
    Boolean changeValidatingById(UpdateItemDto updateItemDto); // 대여

    /**
     * 아이템 페이지 조회
     *
     * @param pageItemDataDto 페이지 조회 데이터 DTO
     */
    PageItemDto getPaginationItem(PageItemDataDto pageItemDataDto);

    /**
     * 아이템 조회
     *
     * @param itemId 조회할 아이템 아이디
     */
    DetailItemDto getDetailById(Long itemId);
}
