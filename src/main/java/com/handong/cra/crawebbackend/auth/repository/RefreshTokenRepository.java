package com.handong.cra.crawebbackend.auth.repository;

import com.handong.cra.crawebbackend.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    public RefreshToken findByRefreshToken(String refreshToken);
    public void deleteAllByUserId(Long userId);
    public void deleteAllByRefreshToken(String refreshToken);
}
