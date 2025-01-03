package com.handong.cra.crawebbackend.havruta.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.DetailHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.ListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.repository.HavrutaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HavrutaServiceImpl implements HavrutaService {
    private final HavrutaRepository havrutaRepository;

    @Override
    @Transactional
    public List<ListHavrutaDto> getAllHavrutas() {

        List<Havruta> havrutas = havrutaRepository.findAll();

        List<ListHavrutaDto> havrutaDtos = havrutas.stream().map(ListHavrutaDto::from).filter(Objects::nonNull).toList();
        return havrutaDtos;
    }

    @Override
    @Transactional
    public DetailHavrutaDto getHavrutaById(Long id) {
        Havruta havruta = havrutaRepository.findById(id).orElseThrow();
        if (havruta.getDeleted()) return null;
        return DetailHavrutaDto.from(havruta);
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
