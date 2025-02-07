package com.handong.cra.crawebbackend.util;

import com.handong.cra.crawebbackend.auth.domain.CustomUserDetails;
import com.handong.cra.crawebbackend.auth.dto.response.ResTokenDto;
import com.handong.cra.crawebbackend.auth.service.CustomUserDetailsService;
import com.handong.cra.crawebbackend.user.domain.User;
import com.handong.cra.crawebbackend.user.domain.UserRoleEnum;
import com.handong.cra.crawebbackend.user.repository.UserRepository;
import com.handong.cra.crawebbackend.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

//    private final UserDetailsService userDetailsService;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = parseJwt(request);

        if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
            if (!jwtTokenProvider.validateToken(jwt)) {
                log.error("만료됨");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료되었습니다.");
                return;
            }

            Long userId = Long.valueOf(jwtTokenProvider.getSubject(jwt));
            String username = userRepository.getUserById(userId).getUsername();
            log.info(username);
            log.info("username: {}", username);
            log.info("role: {}", userRepository.getUserById(userId).getRoles().getAuthorities());
            setAuthentication(username);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

        log.info("=========================================================");
        log.info("=========================================================");
        log.info("=========================================================");
        log.info("=========================================================");
        log.info("username: {}", userDetails.getUser().getUsername());
        log.info("id: {}", userDetails.getUser().getId());
        log.info("role: {}", userDetails.getAuthorities());
        log.info("=========================================================");
        log.info("=========================================================");
        log.info("=========================================================");
        log.info("=========================================================");


        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
        log.info("context: {}", context);
        SecurityContextHolder.setContext(context);
    }

    private String parseJwt(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            log.info(token);
            token = token.substring(7);
            log.info(token);
            return token;
        }
        return null;
    }
}
