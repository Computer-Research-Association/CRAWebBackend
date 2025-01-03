package com.handong.cra.crawebbackend.havruta.service;

import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.ListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.repository.HavrutaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HavrutaServiceImpl implements HavrutaService {

    private final HavrutaRepository havrutaRepository;

    @Override
    public List<ListHavrutaDto> getAllHavrutas() {
        return List.of();
    }

    @Override
    public ListHavrutaDto getHavrutaById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public CreateHavrutaDto createHavruta(CreateHavrutaDto createHavrutaDto) {
        Havruta havruta = Havruta.from(createHavrutaDto);
        havrutaRepository.save(havruta);

        return CreateHavrutaDto
                .builder()
                .className(havruta.getClassName())
                .professor(havruta.getProfessor())
                .build();
    }

    @Override
    @Transactional
    public UpdateHavrutaDto updateHavruta(UpdateHavrutaDto updateHavrutaDto) {
        Havruta havruta = havrutaRepository.findById(updateHavrutaDto.getId()).orElseThrow();
        havruta.update(updateHavrutaDto);
        return UpdateHavrutaDto.from(havruta);
    }

    @Override
    @Transactional
    public Boolean deleteHavruta(Long id) {
        havrutaRepository.findById(id).orElseThrow().delete();
        return true;
    }
}
