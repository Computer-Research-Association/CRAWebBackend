package com.handong.cra.crawebbackend.auth.repository;

import com.handong.cra.crawebbackend.auth.domain.RefreshToken;

public interface RefreshTokenRepository {
    void save(RefreshToken refreshToken);
    RefreshToken findByToken(String token);
    void deleteAllByUserId(Long userId);
}
