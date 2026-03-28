package com.handong.cra.crawebbackend.tag.service;

import com.handong.cra.crawebbackend.tag.domain.Tag;
import com.handong.cra.crawebbackend.tag.dto.request.ReqCreateTagDto;
import com.handong.cra.crawebbackend.tag.dto.request.ReqUpdateTagDto;
import com.handong.cra.crawebbackend.tag.dto.response.ResTagDto;
import com.handong.cra.crawebbackend.tag.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<ResTagDto> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(ResTagDto::from)
                .toList();
    }

    @Override
    public ResTagDto getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found: " + id));
        return ResTagDto.from(tag);
    }

    @Override
    @Transactional
    public ResTagDto createTag(ReqCreateTagDto dto) {
        Tag tag = Tag.of(dto.getName());
        return ResTagDto.from(tagRepository.save(tag));
    }

    @Override
    @Transactional
    public ResTagDto updateTag(Long id, ReqUpdateTagDto dto) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found: " + id));
        tag.updateName(dto.getName());
        return ResTagDto.from(tag);
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found: " + id));
        tagRepository.delete(tag);
    }
}