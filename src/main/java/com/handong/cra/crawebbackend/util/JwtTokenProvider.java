package com.handong.cra.crawebbackend.util;

import com.handong.cra.crawebbackend.auth.domain.RefreshToken;
import com.handong.cra.crawebbackend.auth.dto.ReissueTokenDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResTokenDto;
import com.handong.cra.crawebbackend.auth.repository.RefreshTokenRepository;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    public String generateToken(String username, UserRoleEnum role, Long expirationTime) {
        Key jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());
        log.info("jwtSecretKey: {}", jwtSecretKey);
        Date now = new Date();

        return Jwts.builder()
                .claim("role", role)
                .subject(username)
                .issuedAt(now)
                .issuer("cra/lky")
                .expiration(new Date(now.getTime() + expirationTime))
                .signWith(jwtSecretKey)
                .compact();
    }

    public ResTokenDto generateTokenByLogin(String username, UserRoleEnum role) {
        Long id = userRepository.findByUsername(username).getId();
        String accessToken = generateToken(username, role, accessExpiration);
        String refreshToken = generateToken(username, role, refreshExpiration);
        refreshTokenRepository.save(new RefreshToken(username, refreshToken));
        return new ResTokenDto(id, accessToken, refreshToken);
    }

    @Transactional
    public ResTokenDto reissueToken(ReissueTokenDto reissueTokenDto) {
        RefreshToken savedToken = refreshTokenRepository.getRefreshTokenByUsername(reissueTokenDto.getUsername());
        Long id = userRepository.findByUsername(savedToken.getUsername()).getId();
        if (!savedToken.getRefreshToken().equals(reissueTokenDto.getRefreshToken())) {
            // TODO: exception 하쇼 토큰 구라핑한거
            return null;
        }
        String accessToken = generateToken(reissueTokenDto.getUsername(), reissueTokenDto.getRole(), accessExpiration);
        String newRefreshToken = generateToken(reissueTokenDto.getUsername(), reissueTokenDto.getRole(), refreshExpiration);
        savedToken.setRefreshToken(newRefreshToken);
        return new ResTokenDto(id, accessToken, newRefreshToken);
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
