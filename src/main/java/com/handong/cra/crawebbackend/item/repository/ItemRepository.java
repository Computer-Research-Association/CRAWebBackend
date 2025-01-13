package com.handong.cra.crawebbackend.item.repository;

import com.handong.cra.crawebbackend.item.domain.Item;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    public List<Item> findAllByItemCategoryAndDeletedFalse(ItemCategory itemCategory);
    public Page<Item> findAllByItemCategoryAndDeletedFalse(ItemCategory itemCategory, Pageable pageable);

}
