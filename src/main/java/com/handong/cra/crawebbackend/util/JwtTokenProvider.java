package com.handong.cra.crawebbackend.util;

import com.handong.cra.crawebbackend.auth.domain.RefreshToken;
import com.handong.cra.crawebbackend.auth.dto.ReissueTokenDto;
import com.handong.cra.crawebbackend.auth.dto.TokenDto;
import com.handong.cra.crawebbackend.auth.dto.response.ResTokenDto;
import com.handong.cra.crawebbackend.auth.repository.RefreshTokenRepository;
import com.handong.cra.crawebbackend.exception.auth.AuthInvalidTokenException;
import com.handong.cra.crawebbackend.exception.auth.AuthTokenExpiredException;
import com.handong.cra.crawebbackend.exception.user.UserNotFoundException;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.domain.UserRoleSet;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public String generateToken(Long id, Long expirationTime, UserRoleSet roles) {
        Key jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());
        log.info("jwtSecretKey: {}", jwtSecretKey);
        Date now = new Date();
        List<String> rolesString = new ArrayList<>(List.of());
        roles.getAuthorities().forEach(authority -> {
            rolesString.add(authority.getAuthority());
        });

        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(now)
                .issuer("cra/lky")
                .expiration(new Date(now.getTime() + expirationTime))
                .claims(Map.of("roles", rolesString))
                .signWith(jwtSecretKey)
                .compact();
    }

    public TokenDto generateTokenByLogin(String username) {
        final User user = userRepository.findByUsername(username);
        final Long userId = user.getId();
        final UserRoleSet roles = user.getRoles();
        final String refreshToken = generateToken(userId, refreshExpiration, roles);
        final RefreshToken newRefreshToken = refreshTokenRepository.save(new RefreshToken(userId, refreshToken));
        return TokenDto.of(newRefreshToken.getUserId(), null, newRefreshToken.getRefreshToken());
    }

    @Transactional
    public TokenDto reissueToken(ReissueTokenDto reissueTokenDto) {
        RefreshToken savedToken = refreshTokenRepository.findByRefreshToken(reissueTokenDto.getRefreshToken());

        // 만료되었는지 검사
        if (savedToken == null)
            return TokenDto.of(null, "expired", "expired");
        else if (!validateToken(reissueTokenDto.getRefreshToken()))
            return TokenDto.of(null, "expired", "expired");

            // 잘못된 토큰
        else if (!savedToken.getRefreshToken().equals(reissueTokenDto.getRefreshToken()))
            return TokenDto.of(null, "invalid", "invalid");

        Long userId = savedToken.getUserId();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        UserRoleSet roles = user.getRoles();

        String accessToken = generateToken(userId, accessExpiration, roles);
        return TokenDto.of(userId, accessToken, null);
    }

    // access token 검사
    public Boolean validateToken(String token) {
        SecretKey jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());

        try {
            Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getSubject(String token) {
        SecretKey jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
