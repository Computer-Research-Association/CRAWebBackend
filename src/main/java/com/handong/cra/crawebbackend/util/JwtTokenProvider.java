package com.handong.cra.crawebbackend.util;

import com.handong.cra.crawebbackend.auth.domain.RefreshToken;
import com.handong.cra.crawebbackend.auth.dto.ReissueTokenDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResTokenDto;
import com.handong.cra.crawebbackend.auth.repository.RefreshTokenRepository;
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

    public ResTokenDto generateTokenByLogin(String username) {
        Long userId = userRepository.findByUsername(username).getId();
        String refreshToken = generateToken(userId, refreshExpiration);
        refreshTokenRepository.save(new RefreshToken(userId, refreshToken));
        return ResTokenDto.of(userId, null, refreshToken);
    }

    @Transactional
    public ResTokenDto reissueToken(ReissueTokenDto reissueTokenDto) {
        RefreshToken savedToken = refreshTokenRepository.getRefreshTokenByUserId(reissueTokenDto.getUserId());
        Long userId = savedToken.getUserId();
        if (!savedToken.getRefreshToken().equals(reissueTokenDto.getRefreshToken())) {
            // TODO: exception 하쇼 토큰 구라핑한거
            return null;
        }
        String accessToken = generateToken(reissueTokenDto.getUserId(), accessExpiration);
        return ResTokenDto.of(userId, accessToken, null);
    }


    public Boolean validateToken(String token) {
        SecretKey jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());

        try {
            Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException("Token has expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Invalid token", e);
        }
    }

    public String getSubject(String token) {
        SecretKey jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
