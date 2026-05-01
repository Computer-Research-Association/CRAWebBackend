package com.handong.cra.crawebbackend.auth.repository;

import com.handong.cra.crawebbackend.auth.domain.RefreshToken;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "jwt.refresh-store", havingValue = "db")
public class DbRefreshTokenRepository implements RefreshTokenRepository {
    private final RefreshTokenJpaRepository jpaRepository;

    public DbRefreshTokenRepository(RefreshTokenJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(RefreshToken refreshToken) {
        jpaRepository.save(refreshToken);
    }

    @Override
    public RefreshToken findByToken(String token) {
        return jpaRepository.findByRefreshToken(token);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        jpaRepository.deleteAllByUserId(userId);
    }
}
