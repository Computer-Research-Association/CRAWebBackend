package com.handong.cra.crawebbackend.havruta.service;

import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.DetailHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.ListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;

import java.util.List;

public interface HavrutaService {
    public List<ListHavrutaDto> getAllHavrutas();
    public DetailHavrutaDto getHavrutaById(Long id);
    public CreateHavrutaDto createHavruta(CreateHavrutaDto createHavrutaDto);
    public UpdateHavrutaDto updateHavruta(UpdateHavrutaDto updateHavrutaDto);
    public Boolean deleteHavruta(Long id);
}
