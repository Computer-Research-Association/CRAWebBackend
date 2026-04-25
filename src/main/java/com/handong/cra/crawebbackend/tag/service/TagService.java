package com.handong.cra.crawebbackend.tag.service;

import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.project.dto.ListProjectDto;
import com.handong.cra.crawebbackend.tag.domain.Tag;
import com.handong.cra.crawebbackend.tag.dto.request.ReqCreateTagDto;
import com.handong.cra.crawebbackend.tag.dto.request.ReqUpdateTagDto;
import com.handong.cra.crawebbackend.tag.dto.response.ResTagDto;

import java.util.List;

public interface TagService {
    List<ResTagDto> getAllTags();
    ResTagDto getTagById(Long id);
    ResTagDto createTag(ReqCreateTagDto dto);
    ResTagDto updateTag(Long id, ReqUpdateTagDto dto);
    void deleteTag(Long id);
    List<ListBoardDto> getBoardsByTagName(String tagName);
    List<ListProjectDto> getProjectsByTagName(String tagName);
    List<Tag> getOrCreateTagsByNames(List<String> tagNames);
}
