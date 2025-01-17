package com.handong.cra.crawebbackend.havruta.service;

import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.exception.board.BoardNotFoundException;
import com.handong.cra.crawebbackend.exception.havruta.HavrutaNotFoundException;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.board.domain.Board;
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
        Havruta havruta = havrutaRepository.findById(id).orElseThrow(HavrutaNotFoundException::new);
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
        Havruta havruta = havrutaRepository.findById(updateHavrutaDto.getId()).orElseThrow(HavrutaNotFoundException::new);
        havruta.update(updateHavrutaDto);
        return UpdateHavrutaDto.from(havruta);
    }

    @Override
    @Transactional
    public Boolean deleteHavruta(Long id) {
        // delete managing obj
        Havruta havruta = havrutaRepository.findById(id).orElseThrow(HavrutaNotFoundException::new);
        havruta.delete();

        // delete boards
        List<Board> boards = havruta.getBoards();

        for (Board board : boards) board.delete();
        return true;
    }

    @Override
    public List<ListHavrutaBoardDto> getHavrutaBoardsByHavrutaId(Long id) {
        havrutaRepository.findById(id).orElseThrow(HavrutaNotFoundException::new);
        List<Board> havrutadtos = havrutaRepository.findHavrutaByIdAndDeletedFalse(id).getBoards();

        List<ListHavrutaBoardDto> listHavrutaBoardDtos = havrutadtos.stream()
                .map(ListHavrutaBoardDto::from).filter(Objects::nonNull).toList();
//        if (listHavrutaBoardDtos.isEmpty()) throw new RuntimeException("no data");
        return listHavrutaBoardDtos;
    }

    @Override
    public DetailHavrutaBoardDto getDetailHavrutaBoardByBoardId(Long id) {

        Board board = boardRepository.findBoardByIdAndDeletedFalse(id);
        if (board == null) {
            throw new BoardNotFoundException();
        }
        DetailHavrutaBoardDto detailHavrutaBoardDto = DetailHavrutaBoardDto.from(board);
        return detailHavrutaBoardDto;
    }

    @Override
    @Transactional
    public CreateHavrutaBoardDto createHavrutaBoard(CreateHavrutaBoardDto createHavrutaBoardDto) {
        User user = userRepository.findById(createHavrutaBoardDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Havruta havruta = havrutaRepository.findById(createHavrutaBoardDto.getHavrutaId()).orElseThrow(HavrutaNotFoundException::new);
        Board board = Board.of(user, havruta,createHavrutaBoardDto);
        board = boardRepository.save(board);

        return CreateHavrutaBoardDto.from(board);
    }

    @Override
    @Transactional
    public UpdateHavrutaBoardDto updateHavrutaBoard(Long id, UpdateHavrutaBoardDto updateHavrutaBoardDto) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        board = board.update(updateHavrutaBoardDto);
        return UpdateHavrutaBoardDto.from(board);
    }

//    Delete 기능은 Board에서 처리해도 됨!
//    @Override
//    @Transactional
//    public Boolean deleteHavrutaBoardById(Long id) {
//        boardRepository.findById(id).orElseThrow(() -> new RuntimeException("no data")).delete();
//        return true;
//    }


}
