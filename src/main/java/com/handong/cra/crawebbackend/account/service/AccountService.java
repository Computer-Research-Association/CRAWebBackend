package com.handong.cra.crawebbackend.account.service;

import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.dto.CodeDto;
import com.handong.cra.crawebbackend.account.dto.FindUsernameDto;
import com.handong.cra.crawebbackend.board.dto.PageBoardDataDto;
import com.handong.cra.crawebbackend.board.dto.PageBoardDto;
import com.handong.cra.crawebbackend.item.dto.response.ResAdminDetailItemDto;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.dto.PageUserDataDto;
import com.handong.cra.crawebbackend.user.dto.PageUserDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
import org.hibernate.annotations.processing.Find;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    /**
     * 가입 코드 생성
     *
     * @param length 생성할 코드 수
     */
    List<CodeDto> generateSignupCodes(Short length);

    /**
     * 토큰 확인
     *
     * @param code                토큰 코드
     * @param manageTokenCategory 토큰 종류
     */
    Long codeValidCheck(String code, ManageTokenCategory manageTokenCategory);

    /**
     * 비밀번호 변경 요청 수행
     *
     * @param username 변경할 username
     */
    void requestChangingPassword(String username);

    /**
     * 사용중인 username 인지 검사
     *
     * @param username 확인할 username
     */
    Boolean validUsername(String username);

    /**
     * 유저 username 찾기
     *
     * @param findUsernameDto username 검색시 필요한 정보
     */
    String findUsername(FindUsernameDto findUsernameDto);

    /**
     * 이메일 인증 요창
     *
     * @param email 인증할 이메일
     */
    void emailValidCheck(String email);

    /**
     * 유저 권한 추가
     *
     * @param userId 유저 id
     * @param userRoleEnum 추가할 권한
     */
    void addUserAuthById(Long userId, UserRoleEnum userRoleEnum);

    /**
     * 유저 권한 삭제
     * @param userId 유저 id
     * @param userRoleEnum 제거할 권한
     */
    void removeUserAuthById(Long userId, UserRoleEnum userRoleEnum);

    /**
     * 이름으로 유저 찾기
     *
     * @param name 유저의 이름
     */
    List<UserDetailDto> findUsersByName(String name);

    /**
     * 유저 리스트 페이지네이션
     *
     * @param pageUserDataDto 유저 페이징 처리 DTO
     */
    PageUserDto getPaginationUser(PageUserDataDto pageUserDataDto);

    /**
     * 휴면 계정 활성
     *
     * @param userId 활성화할 유저의 id
     */
    Boolean activeAccount(Long userId);

    /**
     * 유저 삭제
     *
     * @param userId 삭제할 유저의 id
     */
    Boolean deleteUser(Long userId);
}
