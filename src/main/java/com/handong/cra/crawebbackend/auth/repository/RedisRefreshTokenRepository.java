package com.handong.cra.crawebbackend.auth.repository;

import com.handong.cra.crawebbackend.auth.domain.RefreshToken;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "jwt.refresh-store", havingValue = "redis")
public class RedisRefreshTokenRepository implements RefreshTokenRepository{
    private static final String KEY_PREFIX = "refresh_token:";
    private static final String USER_KEY_PREFIX = "refresh_token:user:";

    private final RedisTemplate<String, String> redisTemplate;
    private final Long refreshExpiration;

    public RedisRefreshTokenRepository(
            RedisTemplate<String, String> redisTemplate,
            @Value("${jwt.refresh-expiration}") Long refreshExpiration
    ) {
        this.redisTemplate = redisTemplate;
        this.refreshExpiration = refreshExpiration;
    }

    @Override
    public void save(RefreshToken refreshToken) {
        String tokenKey = KEY_PREFIX + refreshToken.getRefreshToken();
        String userKey = USER_KEY_PREFIX + refreshToken.getUserId();

        redisTemplate.opsForValue().set(
                userKey,
                refreshToken.getRefreshToken(),
                refreshExpiration,
                TimeUnit.MILLISECONDS
        );

        redisTemplate.opsForValue().set(
                tokenKey,
                String.valueOf(refreshToken.getUserId()),
                refreshExpiration,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public RefreshToken findByToken(String token) {
        String key = KEY_PREFIX + token;

        String userIdStr = redisTemplate.opsForValue().get(key);

        return RefreshToken.of(Long.parseLong(userIdStr), token);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        String userKey = USER_KEY_PREFIX + userId;
        String token = redisTemplate.opsForValue().get(userKey);

        String tokenKey = KEY_PREFIX + token;

        if (token != null) {
            redisTemplate.delete(tokenKey);
        }
        redisTemplate.delete(userKey);
    }
}
