package com.handong.cra.crawebbackend.account.service;

import com.handong.cra.crawebbackend.account.domain.ManageTokenCategory;
import com.handong.cra.crawebbackend.account.dto.CodeDto;
import com.handong.cra.crawebbackend.board.dto.PageBoardDataDto;
import com.handong.cra.crawebbackend.board.dto.PageBoardDto;
import com.handong.cra.crawebbackend.item.dto.response.ResAdminDetailItemDto;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.dto.PageUserDataDto;
import com.handong.cra.crawebbackend.user.dto.PageUserDto;
import com.handong.cra.crawebbackend.user.dto.UserDetailDto;
import com.handong.cra.crawebbackend.user.dto.response.ResUserDetailDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    public List<CodeDto> generateSignupCodes(Short length);

    public Long codeValidCheck(String code, ManageTokenCategory manageTokenCategory);

    public void requestChangingPassword(String username);

    public Boolean validUsername(String username);

    public String findUsername(String studentId, String name, String email);

    public void emailValidCheck(String email);

//    public List<UserDetailDto> getUsersByEntranceYear(String year, String term);

    public void updateUserAuthById(Long userId, UserRoleEnum userRoleEnum);

    public List<UserDetailDto> findUsersByName(String name);

    public PageUserDto getPaginationUser(PageUserDataDto pageUserDataDto);

    public Boolean activeAccount(Long userId);

    public Boolean deleteUser(Long userId);
}
