package com.handong.cra.crawebbackend.havruta.service;


import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.DetailHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.ListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.request.ReqCreateHavrutaDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HavrutaService {

    public Boolean deleteHavruta(Long id);

    public UpdateHavrutaDto updateHavruta(UpdateHavrutaDto updateHavrutaDto);

    public DetailHavrutaDto getDetailHavruta(Long id);

    public List<ListHavrutaDto> getListHavruta();

    public CreateHavrutaDto createHavruta(ReqCreateHavrutaDto reqCreateHavrutaDto);
}
