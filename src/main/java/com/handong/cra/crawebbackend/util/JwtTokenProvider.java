package com.handong.cra.crawebbackend.util;

import com.handong.cra.crawebbackend.auth.domain.RefreshToken;
import com.handong.cra.crawebbackend.auth.dto.ReissueTokenDto;
import com.handong.cra.crawebbackend.auth.dto.TokenDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResTokenDto;
import com.handong.cra.crawebbackend.auth.repository.RefreshTokenRepository;
import com.handong.cra.crawebbackend.exception.auth.AuthInvalidTokenException;
import com.handong.cra.crawebbackend.exception.auth.AuthTokenExpiredException;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration}")
    private Long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public String generateToken(Long id, Long expirationTime) {
        Key jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());
        log.info("jwtSecretKey: {}", jwtSecretKey);
        Date now = new Date();

        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(now)
                .issuer("cra/lky")
                .expiration(new Date(now.getTime() + expirationTime))
                .signWith(jwtSecretKey)
                .compact();
    }

    public TokenDto generateTokenByLogin(String username) {
        Long userId = userRepository.findByUsername(username).getId();
        String refreshToken = generateToken(userId, refreshExpiration);
        refreshTokenRepository.save(new RefreshToken(userId, refreshToken));
        return TokenDto.of(userId, null, refreshToken);
    }

    @Transactional
    public TokenDto reissueToken(ReissueTokenDto reissueTokenDto) {
        RefreshToken savedToken = refreshTokenRepository.findByRefreshToken(reissueTokenDto.getRefreshToken());

        if(savedToken == null) throw new AuthTokenExpiredException(); // 401

        // 잘못된 토큰
        if (!savedToken.getRefreshToken().equals(reissueTokenDto.getRefreshToken())) {
            throw new AuthInvalidTokenException();
        }

        // 만료되었는지 검사
        validateToken(reissueTokenDto.getRefreshToken());

        Long userId = savedToken.getUserId();

        String accessToken = generateToken(reissueTokenDto.getUserId(), accessExpiration);
        return TokenDto.of(userId, accessToken, null);
    }


    public Boolean validateToken(String token) {
        SecretKey jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());

        try {
            Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            refreshTokenRepository.deleteAllByRefreshToken(token);
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            return  false;
        }
    }

    public String getSubject(String token) {
        SecretKey jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
