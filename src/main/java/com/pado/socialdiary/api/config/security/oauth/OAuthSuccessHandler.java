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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${front.url}")
    private String frontUrl;

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        AuthorizationDto.TokenResponse tokenResponse = jwtProvider.generateToken(principal);

        response.sendRedirect(
                UriComponentsBuilder.fromUriString(frontUrl + request.getRequestURI())
                        .queryParam("access_token", tokenResponse.getAccessToken())
                        .build()
                        .encode(StandardCharsets.UTF_8)
                        .toUriString()
        );

        log.info("OAuth2SuccessHandler={}",tokenResponse.getAccessToken());
    }


}
