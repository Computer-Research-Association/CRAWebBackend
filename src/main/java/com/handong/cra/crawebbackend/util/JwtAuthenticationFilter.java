package com.handong.cra.crawebbackend.util;

import com.handong.cra.crawebbackend.auth.domain.CustomUserDetails;
import com.handong.cra.crawebbackend.auth.service.CustomUserDetailsService;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException {
        final String jwt = parseJwt(request);
        // null 인 경우 넘어감
        if (jwt != null) {
            // 토큰이 없거나 만료된 경우
            if (!jwtTokenProvider.validateToken(jwt)) {
                log.error("access token 만료됨");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 만료되었습니다.");
                return;
            }
            final Long userId = Long.valueOf(jwtTokenProvider.getSubject(jwt));
            final User user = userRepository.getUserById(userId);
            if (user == null) { // error
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "유저를 찾을 수 없습니다");
                return;
            }
            final String username = user.getUsername();
            setAuthentication(username);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(final String username) {
        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
        SecurityContextHolder.setContext(context);
    }

    private String parseJwt(final HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return token;
        }
        return null;
    }
}
