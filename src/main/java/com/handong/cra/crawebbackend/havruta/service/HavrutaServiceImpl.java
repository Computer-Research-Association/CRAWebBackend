package com.handong.cra.crawebbackend.havruta.service;

import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.ListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;

import java.util.List;

public class HavrutaServiceImpl implements HavrutaService {
    @Override
    public List<ListHavrutaDto> getAllHavrutas() {
        return List.of();
    }

    @Override
    public ListHavrutaDto getHavrutaById(Long id) {
        return null;
    }

    @Override
    public CreateHavrutaDto createHavruta(CreateHavrutaDto createHavrutaDto) {
        return null;
    }

    @Override
    public UpdateHavrutaDto updateHavruta(UpdateHavrutaDto updateHavrutaDto) {
        return null;
    }

    @Override
    public Boolean deleteHavruta(Long id) {
        return null;
    }
}
