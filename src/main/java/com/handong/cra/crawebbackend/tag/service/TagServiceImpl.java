package com.handong.cra.crawebbackend.tag.service;

import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.exception.tag.TagInvalidNameException;
import com.handong.cra.crawebbackend.exception.tag.TagNotFoundException;
import com.handong.cra.crawebbackend.project.dto.ListProjectDto;
import com.handong.cra.crawebbackend.project.repository.ProjectRepository;
import com.handong.cra.crawebbackend.tag.domain.Tag;
import com.handong.cra.crawebbackend.tag.dto.request.ReqCreateTagDto;
import com.handong.cra.crawebbackend.tag.dto.request.ReqUpdateTagDto;
import com.handong.cra.crawebbackend.tag.dto.response.ResTagDto;
import com.handong.cra.crawebbackend.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final BoardRepository boardRepository;

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
                .orElseThrow(TagNotFoundException::new);
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
                .orElseThrow(TagNotFoundException::new);
        tag.updateName(dto.getName());
        return ResTagDto.from(tag);
    }

    @Override
    public List<ListBoardDto> getBoardsByTagName(String tagName) {
        return boardRepository.findByTags_Name(tagName)
                .stream()
                .map(ListBoardDto::from)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public List<ListProjectDto> getProjectsByTagName(String tagName) {
        return projectRepository.findByTags_Name(tagName)
                .stream()
                .map(ListProjectDto::from)
                .toList();
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(TagNotFoundException::new);
        tagRepository.delete(tag);
    }

    @Override
    @Transactional
    public List<Tag> getOrCreateTagsByNames(final List<String> tagNames) {
        final List<String> distinctTagNames = tagNames.stream()
                .map(name -> name == null ? "" : name.trim())
                .distinct()
                .toList();
        if (distinctTagNames.stream().anyMatch(String::isBlank)) {
            throw new TagInvalidNameException();
        }
        final Map<String, Tag> existingTags = tagRepository.findByNameIn(distinctTagNames).stream()
                .collect(Collectors.toMap(Tag::getName, Function.identity()));
        final List<Tag> newTags = distinctTagNames.stream()
                .filter(name -> !existingTags.containsKey(name))
                .map(Tag::of)
                .toList();
        if (!newTags.isEmpty()) {
            try {
                tagRepository.saveAllAndFlush(newTags).forEach(tag -> existingTags.put(tag.getName(), tag));
            } catch (DataIntegrityViolationException e) {
                tagRepository.findByNameIn(distinctTagNames)
                        .forEach(tag -> existingTags.put(tag.getName(), tag));
            }
        }
        return distinctTagNames.stream()
                .map(existingTags::get)
                .toList();
    }

}
