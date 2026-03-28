package com.handong.cra.crawebbackend.board.service;

import com.amazonaws.services.s3.AmazonS3;
import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.domain.BoardPin;
import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.domain.BoardOrderBy;
import com.handong.cra.crawebbackend.board.dto.*;
import com.handong.cra.crawebbackend.board.repository.BoardPinRepository;
import com.handong.cra.crawebbackend.board.repository.BoardRepository;
import com.handong.cra.crawebbackend.exception.auth.AuthForbiddenActionException;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3FileService;
import com.handong.cra.crawebbackend.file.service.S3ImageService;
import com.handong.cra.crawebbackend.exception.board.BoardLikeBadRequestException;
import com.handong.cra.crawebbackend.exception.board.BoardNotFoundException;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.tag.domain.Tag;
import com.handong.cra.crawebbackend.tag.repository.TagRepository;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import com.handong.cra.crawebbackend.util.BoardMDParser;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final S3FileService s3FileService;
    private final AmazonS3 amazonS3;
    private final BoardPinService boardPinService;
    private final EntityManager entityManager;
    private final BoardPinRepository boardPinRepository;
    private final TagRepository tagRepository;


    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.s3.public-url:}")
    private String publicUrl;

    @Override
    @Transactional(readOnly = true)
    public List<ListBoardDto> getBoardsByCategory(Category category) {
        final List<Board> boards = boardRepository
                .findAllByCategoryAndDeletedFalse(category);
        return boards.stream().map(ListBoardDto::from)
                .filter(Objects::nonNull).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PageBoardDto getPaginationBoard(final PageBoardDataDto pageBoardDataDto) {
        final Pageable pageable = getPageable(pageBoardDataDto);
        final Page<Board> boards = boardRepository
                .findAllByCategoryAndDeletedFalse(pageBoardDataDto.getCategory(), pageable);
        final List<BoardPinDto> pins = boardPinService
                .getPinByBoardCategory(pageBoardDataDto.getCategory());
        final List<Board> pinLists = pins.stream().map((pin) ->
                boardRepository
                        .findBoardByIdAndDeletedFalse(pin.getBoardId())
                        .orElseThrow(BoardNotFoundException::new)).toList();
        return PageBoardDto.builder()
                .boardPinDtos(pinLists.stream().map(ListBoardDto::from).toList())
                .listBoardDtos(boards.stream().map(ListBoardDto::from).toList())
                .totalPages(boards.getTotalPages())
                .build();
    }


    @Override
    @Transactional
    public CreateBoardDto createBoard(CreateBoardDto createBoardDto) {
        final User user = userRepository
                .findById(createBoardDto.getUserId()).orElseThrow(UserNotFoundException::new);
        // NOTICE 는  admin 이외엔 작성 불가
        if (createBoardDto.getCategory() == Category.NOTICE &&
                (!user.getRoles().hasRole(UserRoleEnum.ADMIN) /*|| TODO super admin 추가 */)) {
            throw new AuthForbiddenActionException();
        }
        BoardMDParser parser = new BoardMDParser(amazonS3, bucket, publicUrl);
        if (createBoardDto.getFile() != null) {
            final String fileUrl = s3FileService
                    .uploadFile(createBoardDto.getFile(), S3ImageCategory.BOARD);
            createBoardDto.setFileUrl(fileUrl);
        }
        final Board board = Board.of(user, createBoardDto);
        if (!board.getImageUrls().isEmpty()) {
            board.setImageUrls(s3ImageService
                    .transferImage(board.getImageUrls(), S3ImageCategory.BOARD));
            board.setContent(parser
                    .updateImageUrls(board.getContent(), board.getImageUrls()));
        }
        if (createBoardDto.getTagIds() != null) {
            List<Tag> tags = tagRepository.findAllById(createBoardDto.getTagIds());
            board.getTags().addAll(tags);
        }

        final Board saevdBoard = boardRepository.save(board);
        return CreateBoardDto.from(saevdBoard);
    }

    @Override
    @Transactional
    public UpdateBoardDto updateBoard(UpdateBoardDto updateBoardDto) {
        final Board board = boardRepository
                .findById(updateBoardDto.getId())
                .orElseThrow(BoardNotFoundException::new);
        // 권한 검사.
        boardAuthCheck(board.getUser().getId(), updateBoardDto.getUserId());
        BoardMDParser parser = new BoardMDParser(amazonS3, bucket, publicUrl);
        if (updateBoardDto.getIsChangedFile()) {
            String fileUrl = null;
            if (updateBoardDto.getFile() != null) {
                fileUrl = s3FileService
                        .uploadFile(updateBoardDto.getFile(), S3ImageCategory.BOARD);
            }
            updateBoardDto.setFileUrl(fileUrl);
        } else {
            updateBoardDto.setFileUrl(board.getFileUrl());
        }
        if (!updateBoardDto.getImageUrls().isEmpty()) { //img update logic
            // 기존에 있는 이미지와 비교하여 삭제된 이미지 삭제, 새로운 이미지 등록
            final List<String> removeImgs = board.getImageUrls();
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
        final Board updatedBoard = board.update(updateBoardDto);
        return UpdateBoardDto.from(updatedBoard);
    }

    @Override
    @Transactional
    public Boolean deleteBoardById(final UpdateBoardDto updateBoardDto) {
        final Board board = boardRepository
                .findById(updateBoardDto.getId())
                .orElseThrow(BoardNotFoundException::new);
        boardAuthCheck(board.getUser().getId(), updateBoardDto.getUserId()); // 권한 검사
        board.delete();
        if (!board.getImageUrls().isEmpty()) { // 사진 삭제
            s3ImageService.transferImage(board.getImageUrls(), S3ImageCategory.DELETED);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public DetailBoardDto getDetailBoardById(final Long boardId, final Long userId) {
        // 확인하는 유저가 좋아요 누른 글인지 확인
        final Board board = boardRepository
                .findBoardByIdAndDeletedFalse(boardId)
                .orElseThrow(BoardNotFoundException::new);
        final DetailBoardDto detailBoardDto = DetailBoardDto
                .from(board, board.getComments());

        if (userId != null) { // 로그인 유저 로직
            final User user = userRepository.findById(userId)
                    .orElseThrow(UserNotFoundException::new);
            if (user.getDeleted()) { // 휴면 계정인 경우
                throw new UserNotFoundException();
            }
            // 고정 공지인지 확인
            final BoardPin boardPin = boardPinRepository
                    .findBoardPinByBoardIdAndDeletedFalse(board.getId());
            detailBoardDto.setIsPined(false);
            if (boardPin != null) {
                detailBoardDto.setIsPined(true);
                detailBoardDto.setPinId(boardPin.getId());
            }
            final boolean viewerLiked = user.getLikedBoards().contains(board);
            detailBoardDto.setViewerLiked(viewerLiked);
            return detailBoardDto;
        }
        if (!board.getCategory().equals(Category.NOTICE)) { // 비 로그인시 공지 이외 확인시 403

            throw new AuthForbiddenActionException();
        }
        return detailBoardDto;
    }

    @Override
    @Transactional
    public void ascendingBoardView(final Long id) {
        final Board board = boardRepository.findById(id).
                orElseThrow(BoardNotFoundException::new);
        board.increaseView();
    }

    @Override
    @Transactional
    public Integer boardLike(final Long boardId, final Long userId, final Boolean isLiked) {
        final Board board = boardRepository
                .findByIdWithLikedUsers(boardId)
                .orElseThrow(BoardNotFoundException::new);
        final User user = userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (isLiked && !board.getLikedUsers().contains(user)) {
            board.like(user);
            user.likeBoard(board);
        } else if (!isLiked && board.getLikedUsers().contains(user)) {
            board.unlike(user);
            user.unlikeBoard(board);
        } else {
            throw new BoardLikeBadRequestException();
        }
        return board.getLikedUsers().size();
    }


    public Pageable getPageable(final PageBoardDataDto pageBoardDataDto) {
        final HashMap<BoardOrderBy, String> map = new HashMap<>();
        map.put(BoardOrderBy.DATE, "createdAt");
        map.put(BoardOrderBy.LIKECOUNT, "likeCount");
        Sort sort = Sort.by(map.get(pageBoardDataDto.getOrderBy()));
        sort = (pageBoardDataDto.getIsASC()) ? sort.ascending() : sort.descending();
        return PageRequest
                .of(Math.toIntExact(pageBoardDataDto.getPage()), pageBoardDataDto.getPerPage(), sort);
    }

    @Override
    @Transactional(readOnly = true)
    public SearchPageBoardDto searchPaginationBoardsByKeyword(final PageBoardDataDto pageBoardDataDto, final String keyword) {
        final Pageable pageable = getPageable(pageBoardDataDto);
        final List<Board> boards = searchBoards(keyword);
//        final Page<Board> boardPage = new PageImpl<>(boards, pageable, boards.size());
        final int startIndex = (int) pageable.getOffset();
        final int endIndex = Math.min(boards.size(), startIndex + pageable.getPageSize());
        final List<Board> pageBoards = boards.subList(startIndex, endIndex);
        final Page<Board> boardPage = new PageImpl<>(pageBoards, pageable, boards.size());
        return SearchPageBoardDto.builder()
                .listBoardDtos(
                        (!boards.isEmpty())
                                ? boardPage.stream().map(ListBoardDto::from).toList() : List.of())
                .totalPages(boardPage.getTotalPages())
                .totalBoards(boards.size())
                .build();
        // TODO : spring caching
    }

    private List<Board> searchBoards(final String keyword) {
        final SearchSession searchSession = getSearchSession();
        return searchSession.search(Board.class)
                .where(f -> f.bool()
                        .should(f.match().field("title").matching(keyword).boost(2.0f))
                        .should(f.match().field("content").matching(keyword)))
                .fetchHits(1000);
    }

    @Override
    public void setSearchIndex() {
        final SearchSession searchSession = getSearchSession();
        try {
            searchSession.massIndexer(Board.class).startAndWait();
        } catch (InterruptedException e) {
            log.error("fail to set search index");
        }
    }

    private SearchSession getSearchSession() {
        return Search.session(entityManager);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListBoardDto> getBoardsByTagName(String tagName) {
        return boardRepository.findByTags_Name(tagName)
                .stream()
                .map(ListBoardDto::from)
                .toList();
    }

    private void boardAuthCheck(final Long writerId, final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (!writerId.equals(userId) && !user.getRoles().hasRole(UserRoleEnum.ADMIN)) {
            throw new AuthForbiddenActionException();
        }
    }

}
