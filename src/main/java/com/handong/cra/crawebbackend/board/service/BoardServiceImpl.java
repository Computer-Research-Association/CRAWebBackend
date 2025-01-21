package com.handong.cra.crawebbackend.board.service;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.dto.*;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
import com.handong.cra.crawebbackend.exception.board.BoardLikeBadRequestException;
import com.handong.cra.crawebbackend.exception.board.BoardNotFoundException;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final S3ImageService s3ImageService;

    @Override
    @Transactional
    public List<ListBoardDto> getBoardsByCategory(Category category) {
        // get data
        List<Board> boards = boardRepository.findAllByCategoryAndDeletedFalse(category);

        // parsing to dto
        return boards.stream().map(ListBoardDto::from).filter(Objects::nonNull).toList();
    }

    @Override
    public List<ListBoardDto> getPaginationBoard(Category category, Long page, Integer perPage, BoardOrderBy boardOrderBy, Boolean isASC) {

        HashMap<BoardOrderBy, String> map = new HashMap<>();
        map.put(BoardOrderBy.DATE, "createdAt");
        map.put(BoardOrderBy.LIKECOUNT, "likeCount");

        Sort sort = Sort.by(map.get(boardOrderBy));

        sort = (isASC) ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(Math.toIntExact(page), perPage, sort);
        Page<Board> boards = boardRepository.findAllByCategoryAndDeletedFalse(category, pageable);

        return boards.stream().map(ListBoardDto::from).toList();
    }

    @Override
    @Transactional
    public CreateBoardDto createBoard(CreateBoardDto createBoardDto) {
        User user = userRepository.findById(createBoardDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Board board = Board.of(user, createBoardDto);
        board.setImageUrls(s3ImageService.transferImage(board.getImageUrls(), S3ImageCategory.BOARD));
        board = boardRepository.save(board);
        return CreateBoardDto.from(board);
    }

    @Override
    @Transactional
    public UpdateBoardDto updateBoard(UpdateBoardDto updateBoardDto) {

        Board board = boardRepository.findById(updateBoardDto.getId()).orElseThrow(BoardNotFoundException::new);
        //img update logic
        List<String> removeImgs = board.getImageUrls();
        List<String> newImgs = updateBoardDto.getImageUrls();

        List<String> temp = new ArrayList<>(removeImgs);
        temp.retainAll(newImgs);

        removeImgs.removeAll(temp);
        newImgs.removeAll(temp);

        s3ImageService.transferImage(removeImgs,S3ImageCategory.DELETED);
        newImgs = s3ImageService.transferImage(newImgs,S3ImageCategory.BOARD);
        newImgs.addAll(temp);


        board = board.update(updateBoardDto);
        board.setImageUrls(newImgs);


        return UpdateBoardDto.from(board);
    }

    @Override
    @Transactional
    public Boolean deleteBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(UserNotFoundException::new);
        board.delete();
        s3ImageService.transferImage(board.getImageUrls(), S3ImageCategory.DELETED);

        return true;
    }

    @Override
    public DetailBoardDto getDetailBoardById(Long id) {
        Board board = boardRepository.findBoardByIdAndDeletedFalse(id);
        if (board == null) {
            throw new BoardNotFoundException();
        }
        return DetailBoardDto.from(board);
    }

    @Override
    @Transactional
    public void ascendingBoardView(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("no data"));
        board.increaseView();
    }

    @Override
    @Transactional
    public void boardLike(Long boardId, Long userId, Boolean isLiked) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (isLiked && !board.getLikedUsers().contains(user)) {
            log.info("Add Listing");
            board.like(user);
            user.likeBoard(board);
        } else if (!isLiked && board.getLikedUsers().contains(user)) {
            log.info("Remove Listing");
            board.unlike(user);
            user.unlikeBoard(board);
        } else {
            // exception
            throw new BoardLikeBadRequestException();
        }

        log.info("user list size = {}, board list size = {}", user.getLikedBoards().size(), board.getLikedUsers().size());
    }
}
