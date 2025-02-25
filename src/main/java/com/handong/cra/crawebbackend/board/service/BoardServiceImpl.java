package com.handong.cra.crawebbackend.board.service;

import com.amazonaws.services.s3.AmazonS3;
import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.dto.*;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.exception.auth.AuthForbiddenActionException;
import com.handong.cra.crawebbackend.exception.havruta.HavrutaNotFoundException;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3FileService;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
import com.handong.cra.crawebbackend.exception.board.BoardLikeBadRequestException;
import com.handong.cra.crawebbackend.exception.board.BoardNotFoundException;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.havruta.domain.Havruta;
import com.handong.cra.crawebbackend.havruta.repository.HavrutaRepository;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import com.handong.cra.crawebbackend.util.BoardMDParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final HavrutaRepository havrutaRepository;
    private final UserRepository userRepository;
    private final S3ImageService s3ImageService;
    private final S3FileService s3FileService;
    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    @Transactional
    public List<ListBoardDto> getBoardsByCategory(Category category) {
        // get data
        List<Board> boards = boardRepository.findAllByCategoryAndDeletedFalse(category);

        // parsing to dto
        return boards.stream().map(ListBoardDto::from).filter(Objects::nonNull).toList();
    }

    @Override
    public List<ListBoardDto> getPaginationBoard(PageBoardDto pageBoardDto) {

        Pageable pageable = getPageable(pageBoardDto);
        Page<Board> boards = boardRepository.findAllByCategoryAndDeletedFalse(pageBoardDto.getCategory(), pageable);

        return boards.stream().map(ListBoardDto::from).toList();
    }


    @Override
    @Transactional
    public CreateBoardDto createBoard(CreateBoardDto createBoardDto) {

        User user = userRepository.findById(createBoardDto.getUserId()).orElseThrow(UserNotFoundException::new);

        // NOTICE 는  admin 이외엔 작성 불가
        if (createBoardDto.getCategory() == Category.NOTICE && (!user.getRoles().hasRole(UserRoleEnum.ADMIN) /*|| TODO super admin 추가 */))
            throw new AuthForbiddenActionException();


        BoardMDParser parser = new BoardMDParser(amazonS3, bucket);
        if (createBoardDto.getFile() != null) {
            String fileUrl = s3FileService.uploadFile(createBoardDto.getFile(), S3ImageCategory.BOARD);
            createBoardDto.setFileUrl(fileUrl);
        }
        Board board;

        // havruta
        if (createBoardDto.getHavrutaDto() != null) {
            Havruta havruta = havrutaRepository.findById(createBoardDto.getHavrutaDto().getId()).orElseThrow(HavrutaNotFoundException::new);
            board = Board.of(user, havruta, createBoardDto);
        } else {
            board = Board.of(user, createBoardDto);
        }

        if (!board.getImageUrls().isEmpty()) {
            board.setImageUrls(s3ImageService.transferImage(board.getImageUrls(), S3ImageCategory.BOARD));
            board.setContent(parser.updateImageUrls(board.getContent(), board.getImageUrls()));
        }

        board = boardRepository.save(board);
        return CreateBoardDto.from(board);
    }

    @Override
    @Transactional
    public UpdateBoardDto updateBoard(UpdateBoardDto updateBoardDto) {
        Board board = boardRepository.findById(updateBoardDto.getId()).orElseThrow(BoardNotFoundException::new);

        // 권한 검사.
        boardAuthCheck(board.getUser().getId(), updateBoardDto.getUserId());

        BoardMDParser parser = new BoardMDParser(amazonS3, bucket);

        if (updateBoardDto.getIsChangedFile()) {
            String fileUrl = null;
            if (updateBoardDto.getFile() != null) {

                fileUrl = s3FileService.uploadFile(updateBoardDto.getFile(), S3ImageCategory.BOARD);
            }
            log.info(fileUrl);
            updateBoardDto.setFileUrl(fileUrl);
        } else {
            updateBoardDto.setFileUrl(board.getFileUrl());
        }

        if (!updateBoardDto.getImageUrls().isEmpty()) {
            //img update logic

            // 기존에 있는 이미지와 비교하여 삭제된 이미지 삭제, 새로운 이미지 등록
            List<String> removeImgs = board.getImageUrls();
            List<String> newImgs = updateBoardDto.getImageUrls();

            List<String> temp = new ArrayList<>(removeImgs);
            temp.retainAll(newImgs);

            removeImgs.removeAll(temp);
            newImgs.removeAll(temp);

            s3ImageService.transferImage(removeImgs, S3ImageCategory.DELETED);
            newImgs = s3ImageService.transferImage(newImgs, S3ImageCategory.BOARD);
            newImgs.addAll(temp);

            board.setImageUrls(newImgs);
            board.setContent(parser.updateImageUrls(board.getContent(), board.getImageUrls()));
        }
        board = board.update(updateBoardDto);


        return UpdateBoardDto.from(board);
    }

    @Override
    @Transactional
    public Boolean deleteBoardById(UpdateBoardDto updateBoardDto) {
        Board board = boardRepository.findById(updateBoardDto.getId()).orElseThrow(BoardNotFoundException::new);

        // 권한 검사
        boardAuthCheck(board.getUser().getId(), updateBoardDto.getUserId());
        board.delete();

        // 사진 삭제
        if (!board.getImageUrls().isEmpty())
            s3ImageService.transferImage(board.getImageUrls(), S3ImageCategory.DELETED);

        return true;
    }

    @Override
    public DetailBoardDto getDetailBoardById(Long id, Long userId) {
        // 확인하는 유저가 좋아요 누른 글인지 확인
        User user = null;
        boolean viewerLiked;

        if (userId != null) user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Board board = boardRepository.findBoardByIdAndDeletedFalse(id);

        // 글이 없으면 404
        if (board == null) throw new BoardNotFoundException();

        // 로그인 되어있다면
        if (user != null) {
            viewerLiked = user.getLikedBoards().contains(board);
            return DetailBoardDto.from(board, viewerLiked);
        } else { // 로그인 안되어있을때
            // 공지 이외 확인 불가
            if (!board.getCategory().equals(Category.NOTICE)) throw new AuthForbiddenActionException();
        }

        return DetailBoardDto.from(board, board.getComments());

    }

    @Override
    @Transactional
    public void ascendingBoardView(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        board.increaseView();
    }

    @Override
    @Transactional
    public Integer boardLike(Long boardId, Long userId, Boolean isLiked) {
        Board board = boardRepository.findByIdWithLikedUsers(boardId).orElseThrow(BoardNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (isLiked && !board.getLikedUsers().contains(user)) {
            board.like(user);
            user.likeBoard(board);
        } else if (!isLiked && board.getLikedUsers().contains(user)) {
            board.unlike(user);
            user.unlikeBoard(board);
        } else {
            // exception
            throw new BoardLikeBadRequestException();
        }
        return board.getLikedUsers().size();
    }


    private Pageable getPageable(PageBoardDto pageBoardDto) {
        HashMap<BoardOrderBy, String> map = new HashMap<>();
        map.put(BoardOrderBy.DATE, "createdAt");
        map.put(BoardOrderBy.LIKECOUNT, "likeCount");

        Sort sort = Sort.by(map.get(pageBoardDto.getOrderBy()));
        sort = (pageBoardDto.getIsASC()) ? sort.ascending() : sort.descending();
        return PageRequest.of(Math.toIntExact(pageBoardDto.getPage()), pageBoardDto.getPerPage(), sort);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // havruta board


    @Override
    public List<ListBoardDto> getHavrutaBoards() {

        List<Board> boards = boardRepository.findByCategory(Category.HAVRUTA);

        return boards.stream()
                .map(ListBoardDto::from).filter(Objects::nonNull).toList();
    }

    @Override
    public List<ListBoardDto> getHavrutaBoardsByHavrutaId(Long havrutaId) {
        Havruta havruta = havrutaRepository.findById(havrutaId).orElseThrow(HavrutaNotFoundException::new);
        List<Board> havrutas = havruta.getBoards();

        return havrutas.stream()
                .map(ListBoardDto::from).filter(Objects::nonNull).toList();
    }

    @Override
    public List<ListBoardDto> getPaginationAllHavrutaBoard(PageBoardDto pageBoardDto) {

        Pageable pageable = getPageable(pageBoardDto);

        Page<Board> boards = boardRepository.findByCategoryAndDeletedFalse(Category.HAVRUTA, pageable);

        return boards.stream().map(ListBoardDto::from).toList();
    }

    @Override
    public List<ListBoardDto> getPaginationHavrutaBoard(Long havrutaId, PageBoardDto pageBoardDto) {

        Pageable pageable = getPageable(pageBoardDto);

        Havruta havruta = havrutaRepository.findById(havrutaId).orElseThrow();
        Page<Board> boards = boardRepository.findAllByHavrutaAndDeletedFalse(havruta, pageable);

        return boards.stream().map(ListBoardDto::from).toList();
    }

    private void boardAuthCheck(Long writerId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (!writerId.equals(userId) && !user.getRoles().hasRole(UserRoleEnum.ADMIN))
            throw new AuthForbiddenActionException();
    }

}
