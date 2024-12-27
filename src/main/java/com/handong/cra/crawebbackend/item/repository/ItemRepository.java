package com.handong.cra.crawebbackend.item.repository;

import com.handong.cra.crawebbackend.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
