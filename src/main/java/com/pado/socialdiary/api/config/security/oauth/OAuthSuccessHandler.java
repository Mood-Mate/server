package com.pado.socialdiary.api.config.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pado.socialdiary.api.common.dto.AuthorizationDto;
import com.pado.socialdiary.api.config.security.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        AuthorizationDto.TokenResponse tokenResponse = jwtProvider.generateToken(principal);

        ServletOutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, tokenResponse.getAccessToken());
        outputStream.flush();

        log.info("OAuth2SuccessHandler={}",tokenResponse.getAccessToken());
    }
}
