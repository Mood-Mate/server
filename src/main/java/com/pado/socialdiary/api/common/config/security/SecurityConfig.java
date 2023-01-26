package com.pado.socialdiary.api.common.config.security;

import com.pado.socialdiary.api.common.config.security.oauth.OAuthLoginService;
import com.pado.socialdiary.api.common.config.security.oauth.OAuthSuccessHandler;
import com.pado.socialdiary.api.member.entity.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final OAuthLoginService oAuthLoginService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    
    private final String[] AUTH_PASS_PATH = {"/swagger-ui/**", "/v3/api-docs/**", "/api/member/**", "/api/diary/**", "/api/follow/**"};
    private final String[] AUTH_CHECK_PATH = {"/api/member/auth", "/api/diary", "/api/follow"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                    .requestMatchers(AUTH_PASS_PATH).permitAll()
                    .requestMatchers(AUTH_CHECK_PATH).hasRole(MemberRole.USER.getRole())
                .anyRequest()
                    .authenticated()
                .and()
                .oauth2Login()
                    .successHandler(oAuthSuccessHandler)
                    .userInfoEndpoint()
                    .userService(oAuthLoginService);

                httpSecurity.addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
