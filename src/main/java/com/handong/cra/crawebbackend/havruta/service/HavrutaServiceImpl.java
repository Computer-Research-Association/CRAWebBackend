package com.handong.cra.crawebbackend.havruta.service;

import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.exception.auth.AuthForbiddenActionException;
import com.handong.cra.crawebbackend.exception.board.BoardNotFoundException;
import com.handong.cra.crawebbackend.exception.havruta.HavrutaNotFoundException;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3FileService;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
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
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class HavrutaServiceImpl implements HavrutaService {
    private final HavrutaRepository havrutaRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final S3ImageService s3ImageService;
    private final S3FileService s3FileService;

    @Override
    public List<ListHavrutaDto> getAllHavrutas() {

        List<Havruta> havrutas = havrutaRepository.findAll();

        List<ListHavrutaDto> havrutaDtos = havrutas.stream().map(ListHavrutaDto::from).filter(Objects::nonNull).toList();
        return havrutaDtos;
    }

    @Override
    public DetailHavrutaDto getHavrutaById(Long id) {
        Havruta havruta = havrutaRepository.findById(id).orElseThrow(HavrutaNotFoundException::new);
        if (havruta.getDeleted()) throw new HavrutaNotFoundException();
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
    public List<ListHavrutaBoardDto> getHavrutaBoards(){

        List<Board> boards = boardRepository.findByCategory(Category.HAVRUTA);

        List<ListHavrutaBoardDto> listHavrutaBoardDtos = boards.stream()
                .map(ListHavrutaBoardDto::from).filter(Objects::nonNull).toList();
        return listHavrutaBoardDtos;
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
    public List<ListHavrutaBoardDto> getPaginationAllHavrutaBoard(Long page, Integer perPage, BoardOrderBy boardOrderBy, Boolean isASC){
        HashMap<BoardOrderBy, String> map = new HashMap<>();
        map.put(BoardOrderBy.DATE, "createdAt");
        map.put(BoardOrderBy.LIKECOUNT, "likeCount");

        Sort sort = Sort.by(map.get(boardOrderBy));
        sort = (isASC) ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(Math.toIntExact(page), perPage, sort);

        Page<Board> boards = boardRepository.findByCategoryAndDeletedFalse(Category.HAVRUTA, pageable);

        return boards.stream().map(ListHavrutaBoardDto::from).toList();
    }

    @Override
    public List<ListHavrutaBoardDto> getPaginationHavrutaBoard(Long id, Long page, Integer perPage, BoardOrderBy boardOrderBy, Boolean isASC) {
        HashMap<BoardOrderBy, String> map = new HashMap<>();
        map.put(BoardOrderBy.DATE, "createdAt");
        map.put(BoardOrderBy.LIKECOUNT, "likeCount");

        Sort sort = Sort.by(map.get(boardOrderBy));
        sort = (isASC) ? sort.ascending() : sort.descending();
        Havruta havruta = havrutaRepository.findById(id).orElseThrow();
        Pageable pageable = PageRequest.of(Math.toIntExact(page), perPage, sort);

        Page<Board> boards = boardRepository.findAllByHavrutaAndDeletedFalse(havruta, pageable);

        return boards.stream().map(ListHavrutaBoardDto::from).toList();
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

        List<String> fileUrls = s3FileService.uploadFiles(createHavrutaBoardDto.getFiles(), S3ImageCategory.BOARD);
        createHavrutaBoardDto.setFileUrls(fileUrls);

        Board board = Board.of(user, havruta,createHavrutaBoardDto);
        if (!board.getImageUrls().isEmpty())
            board.setImageUrls(s3ImageService.transferImage(board.getImageUrls(), S3ImageCategory.BOARD));

        board = boardRepository.save(board);

        return CreateHavrutaBoardDto.from(board);
    }

    @Override
    @Transactional
    public UpdateHavrutaBoardDto updateHavrutaBoard(UpdateHavrutaBoardDto updateHavrutaBoardDto) {
        Long id = updateHavrutaBoardDto.getId();
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        User user = userRepository.findById(updateHavrutaBoardDto.getUserId()).orElseThrow(UserNotFoundException::new);

        if (!user.getId().equals(board.getUser().getId()) || !user.getRoles().hasRole(UserRoleEnum.ADMIN))
            throw new AuthForbiddenActionException();

        List<String> fileUrls = s3FileService.uploadFiles(updateHavrutaBoardDto.getFiles(), S3ImageCategory.BOARD);
        updateHavrutaBoardDto.setFileUrls(fileUrls);

        if (!updateHavrutaBoardDto.getImageUrls().isEmpty()) {
            //img update logic
            List<String> removeImgs = board.getImageUrls();
            List<String> newImgs = updateHavrutaBoardDto.getImageUrls();

            List<String> temp = new ArrayList<>(removeImgs);
            temp.retainAll(newImgs);

            removeImgs.removeAll(temp);
            newImgs.removeAll(temp);

            s3ImageService.transferImage(removeImgs, S3ImageCategory.DELETED);
            newImgs = s3ImageService.transferImage(newImgs, S3ImageCategory.BOARD);
            newImgs.addAll(temp);

            board.setImageUrls(newImgs);
        }

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
