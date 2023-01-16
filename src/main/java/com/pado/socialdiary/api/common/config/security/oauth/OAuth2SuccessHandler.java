package com.pado.socialdiary.api.common.config.security.oauth;

import com.pado.socialdiary.api.common.config.security.JwtProvider;
import com.pado.socialdiary.api.common.dto.response.TokenResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        TokenResponse tokenResponse = jwtProvider.generateToken(principal);

        log.info("OAuth2SuccessHandler={}",tokenResponse.getAccessToken());
        response.setHeader("Authorization", tokenResponse.getAccessToken());
    }
}
