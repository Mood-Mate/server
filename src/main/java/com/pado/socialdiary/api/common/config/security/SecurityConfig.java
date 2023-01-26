package com.pado.socialdiary.api.common.config.security;

import com.pado.socialdiary.api.common.config.security.oauth.OAuthLoginService;
import com.pado.socialdiary.api.common.config.security.oauth.OAuthSuccessHandler;
import com.pado.socialdiary.api.member.entity.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final OAuthLoginService oAuthLoginService;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    private final String[] AUTH_PASS_PATH = {"/swagger-ui/**", "/v3/api-docs/**", "/api/member/**", "/api/diary/**"};
    private final String[] AUTH_CHECK_PATH = {"/api/member/auth", "/api/diary"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(configurer -> configurer.configurationSource(configurationSource()))
                .csrf().disable()
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

    @Bean
    CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
