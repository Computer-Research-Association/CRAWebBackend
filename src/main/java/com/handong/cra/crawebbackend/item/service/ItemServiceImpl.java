package com.handong.cra.crawebbackend.item.service;

import com.handong.cra.crawebbackend.exception.auth.AuthForbiddenActionException;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
import com.handong.cra.crawebbackend.exception.item.ItemNotFoundException;
import com.handong.cra.crawebbackend.item.domain.Item;
import com.handong.cra.crawebbackend.item.dto.*;
import com.handong.cra.crawebbackend.item.repository.ItemRepository;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final S3ImageService s3ImageService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreateItemDto createItem(final CreateItemDto createItemDto) {
        itemAuthCheck(createItemDto.getUserId()); // 권한 검사
        if (createItemDto.getImageUrl() != null) {
            createItemDto.setImageUrl(
                    s3ImageService.transferImage(createItemDto.getImageUrl(), S3ImageCategory.ITEM));
        }
        final Item item = Item.from(createItemDto);
        final Item savedItem = itemRepository.save(item);
        return CreateItemDto.from(savedItem);
    }

    @Override
    @Transactional
    public UpdateItemDto updateItem(final UpdateItemDto updateItemDto) {
        itemAuthCheck(updateItemDto.getUserId()); // 권한 검사
        final Item item = itemRepository.findById(updateItemDto.getId())
                .orElseThrow(ItemNotFoundException::new);
        if (updateItemDto.getImageUrl().contains("temp")) { // 변경됨
            s3ImageService.transferImage(item.getImageUrl(), S3ImageCategory.DELETED);
            updateItemDto.setImageUrl(
                    s3ImageService.transferImage(updateItemDto.getImageUrl(), S3ImageCategory.ITEM));
        }
        final Item updatedItem = item.update(updateItemDto);
        return UpdateItemDto.from(updatedItem);
    }

    @Override
    @Transactional
    public Boolean deleteItemById(final UpdateItemDto updateItemDto) {
        itemAuthCheck(updateItemDto.getUserId()); // 권한 검사
        final Item item = itemRepository.findById(updateItemDto.getId())
                .orElseThrow(ItemNotFoundException::new);
        item.delete();
        if (item.getImageUrl() != null) {
            s3ImageService.transferImage(
                    item.getImageUrl(), S3ImageCategory.DELETED);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean changeValidatingById(final UpdateItemDto updateItemDto) {
        itemAuthCheck(updateItemDto.getUserId()); // 권한 검사
        final User user = userRepository.
                findByUsername(updateItemDto.getBorrowerUsername());
        if (user == null) {
            throw new UserNotFoundException();
        }
        final Item item = itemRepository.findById(updateItemDto.getId())
                .orElseThrow(ItemNotFoundException::new);
        item.setBorrowerUser(user);
        item.setIsBorrowed(updateItemDto.getIsBorrowed());
        return true;
    }

    @Override
    public PageItemDto getPaginationItem(final PageItemDataDto pageItemDataDto) {
        Sort sort = Sort.by("createdAt");
        sort = (pageItemDataDto.getIsASC()) ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.
                of(Math.toIntExact(pageItemDataDto.getPage()), pageItemDataDto.getPerPage(), sort);
        final Page<Item> pages = itemRepository
                .findAllByItemCategoryAndDeletedFalse(pageItemDataDto.getItemCategory(), pageable);
        return PageItemDto.of(pages.stream().map(ListItemDto::from).toList(), pages.getTotalPages());
    }

    @Override
    public DetailItemDto getDetailById(final Long itemId) {
        final Item item = itemRepository.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);
        if (item.getDeleted()) {
            return null;
        }
        return DetailItemDto.from(item);
    }

    private void itemAuthCheck(final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (!user.getRoles().hasRole(UserRoleEnum.ADMIN)) {
            throw new AuthForbiddenActionException();
        }

    }
}
