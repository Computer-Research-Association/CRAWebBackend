package com.handong.cra.crawebbackend.havruta.service;

import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.ListBoardDto;
import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.havruta.dto.CreateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.DetailHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.ListHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.UpdateHavrutaDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.CreateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.DetailHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.ListHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.UpdateHavrutaBoardDto;
import com.handong.cra.crawebbackend.havruta.repository.HavrutaRepository;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HavrutaServiceImpl implements HavrutaService {
    private final HavrutaRepository havrutaRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

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
    @Transactional
    public CreateHavrutaDto createHavruta(CreateHavrutaDto createHavrutaDto) {
        Havruta havruta = Havruta.from(createHavrutaDto);
        havrutaRepository.save(havruta);

        return CreateHavrutaDto
                .builder()
                .id(havruta.getId())
                .createdAt(havruta.getCreatedAt())
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
    public Boolean deleteHavruta(Long id) {
        havrutaRepository.findById(id).orElseThrow().delete();
        return true;
    }

    @Override
    public List<ListHavrutaBoardDto> getHavrutaBoardsByHavrutaId(Long id) {

        List<Board> havrutadtos = havrutaRepository.findHavrutaDeletedFalseById(id).getBoards();
        List<ListHavrutaBoardDto> listHavrutaBoardDtos = havrutadtos.stream()
                .map(ListHavrutaBoardDto::from).toList();

        return listHavrutaBoardDtos;
    }

    @Override
    public DetailHavrutaBoardDto getDetailHavrutaBoardByBoardId(Long id) {

        Board board = boardRepository.findBoardDeletedFalseById(id);
        DetailHavrutaBoardDto detailHavrutaBoardDto = DetailHavrutaBoardDto.from(board);
        return detailHavrutaBoardDto;
    }

    @Override
    @Transactional
    public CreateHavrutaBoardDto createHavrutaBoard(CreateHavrutaBoardDto createHavrutaBoardDto) {
        User user = userRepository.findById(createHavrutaBoardDto.getUserId()).orElseThrow(()->new RuntimeException("no user"));
        Havruta havruta = havrutaRepository.findById(createHavrutaBoardDto.getHavrutaId()).orElseThrow(()->new RuntimeException("no havrutaid"));
        Board board = Board.of(user,havruta,createHavrutaBoardDto);
        board = boardRepository.save(board);

        return CreateHavrutaBoardDto.from(board);
    }

    @Override
    @Transactional
    public UpdateHavrutaBoardDto updateHavrutaBoard(UpdateHavrutaBoardDto updateHavrutaBoardDto) {
        return null;
    }

    @Override
    @Transactional
    public Boolean deleteHavrutaBoardById(Long id) {
        return null;
    }

}
