package com.handong.cra.crawebbackend.havruta.service;

import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.DetailHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.ListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.CreateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.DetailHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.ListHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.UpdateHavrutaBoardDto;

import java.util.List;

public interface HavrutaService {
    public List<ListHavrutaDto> getAllHavrutas();
    public DetailHavrutaDto getHavrutaById(Long id);
    public CreateHavrutaDto createHavruta(CreateHavrutaDto createHavrutaDto);
    public UpdateHavrutaDto updateHavruta(UpdateHavrutaDto updateHavrutaDto);
    public Boolean deleteHavruta(Long id);

    public List<ListHavrutaBoardDto> getHavrutaBoardsByHavrutaId(Long id);
    public DetailHavrutaBoardDto getDetailHavrutaBoardByBoardId(Long id);
//    public List<ListBoardDto> getPaginationBoard(Long page, Integer perPage, BoardOrderBy orderBy, Boolean isASC);
    public CreateHavrutaBoardDto createHavrutaBoard(CreateHavrutaBoardDto createHavrutaBoardDto);
    public UpdateHavrutaBoardDto updateHavrutaBoard(Long id, UpdateHavrutaBoardDto updateHavrutaBoardDto);
//    public Boolean deleteHavrutaBoardById(Long id);

}
