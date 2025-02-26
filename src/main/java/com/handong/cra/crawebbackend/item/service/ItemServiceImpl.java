package com.handong.cra.crawebbackend.item.service;

import com.handong.cra.crawebbackend.exception.auth.AuthForbiddenActionException;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
import com.handong.cra.crawebbackend.exception.item.ItemNotFoundException;
import com.handong.cra.crawebbackend.item.domain.Item;
import com.handong.cra.crawebbackend.item.domain.ItemCategory;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final S3ImageService s3ImageService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreateItemDto createItem(CreateItemDto createItemDto) {
        // 권한 검사
        itemAuthCheck(createItemDto.getUserId());

        if (createItemDto.getImageUrl() != null)
            createItemDto.setImageUrl(s3ImageService.transferImage(createItemDto.getImageUrl(), S3ImageCategory.ITEM));

        Item item = Item.from(createItemDto);
        item = itemRepository.save(item);
        return CreateItemDto.from(item);
    }

    @Override
    @Transactional
    public UpdateItemDto updateItem(UpdateItemDto updateItemDto) {
        // 권한 검사
        itemAuthCheck(updateItemDto.getUserId());

        Item item = itemRepository.findById(updateItemDto.getId()).orElseThrow(ItemNotFoundException::new);

        if (updateItemDto.getImageUrl().contains("temp")) { // 변경됨
            s3ImageService.transferImage(item.getImageUrl(), S3ImageCategory.DELETED);
            updateItemDto.setImageUrl(s3ImageService.transferImage(updateItemDto.getImageUrl(), S3ImageCategory.ITEM));
        }

        item = item.update(updateItemDto);
        return UpdateItemDto.from(item);
    }

    @Override
    @Transactional
    public Boolean deleteItemById(UpdateItemDto updateItemDto) {
        // 권한 검사
        itemAuthCheck(updateItemDto.getUserId());

        Item item = itemRepository.findById(updateItemDto.getId()).orElseThrow(ItemNotFoundException::new);

        item.delete();

        if (item.getImageUrl() != null)
            s3ImageService.transferImage(item.getImageUrl(), S3ImageCategory.DELETED);

        return true;
    }

    @Override
    @Transactional
    public Boolean changeValidatingById(UpdateItemDto updateItemDto) {
        // 권한 검사
        itemAuthCheck(updateItemDto.getUserId());


        User user = userRepository.findByUsername(updateItemDto.getBorrowerUsername());
        if (user == null) throw new UserNotFoundException();

        Item item = itemRepository.findById(updateItemDto.getId()).orElseThrow(ItemNotFoundException::new);

        item.setBorrowerUser(user);

        item.setIsBorrowed(updateItemDto.getIsBorrowed());
        return true;
    }

    @Override
    public PageItemDto getPaginationItem(PageItemDataDto pageItemDataDto) {
        Sort sort = Sort.by("createdAt");

        sort = (pageItemDataDto.getIsASC()) ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(Math.toIntExact(pageItemDataDto.getPage()), pageItemDataDto.getPerPage(), sort);
        Page<Item> pages = itemRepository.findAllByItemCategoryAndDeletedFalse(pageItemDataDto.getItemCategory(), pageable);

        return PageItemDto.of(pages.stream().map(ListItemDto::from).toList(), pages.getTotalPages());
    }


    @Override
    public List<ListItemDto> getItemsByCategory(ItemCategory itemCategory) {
        List<Item> items = itemRepository.findAllByItemCategoryAndDeletedFalse(itemCategory);
        return items.stream().map(ListItemDto::from).toList();
    }


    @Override
    public DetailItemDto getDetailById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        if (item.getDeleted()) return null;
        else return DetailItemDto.from(item);
    }

    private void itemAuthCheck(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (!user.getRoles().hasRole(UserRoleEnum.ADMIN)) throw new AuthForbiddenActionException();

    }

}
