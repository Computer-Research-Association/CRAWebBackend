package com.handong.cra.crawebbackend.user.service;

import com.handong.cra.crawebbackend.user.dto.CacheUserDto;

public interface JwtUserService {
    public CacheUserDto getUserById(Long userId);
}
