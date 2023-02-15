package com.pado.socialdiary.api.config.security;

import com.pado.socialdiary.api.common.dto.AuthorizationDto;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.mapper.MemberMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;
    private final String CLAIM_KEY = "auth";
    private final MemberMapper memberMapper;
    private final String TOKEN_GRANT_TYPE = "Bearer";

    public JwtProvider(@Value("${jwt.secret.key}") String jwtSecretKey, MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

//    토큰 유효시간 -> 1시간
    private static Date tokenExpireIn() {
        return new Date(new Date().getTime() + 3600000);
    }

    public AuthorizationDto.TokenResponse generateToken(Authentication authentication) {

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String generatedToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(tokenExpireIn())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return AuthorizationDto.TokenResponse.builder()
                .grantType(TOKEN_GRANT_TYPE)
                .accessToken(generatedToken)
                .build();
    }

    public AuthorizationDto.TokenResponse generateToken(OAuth2User authentication) {
        String email = (String) authentication.getAttributes().get("email");
        System.out.println(authentication.getAttributes());

        /**
         * 인증 발행사 별 프로퍼티가 다르니 이메일 가져올 때 분기 체크하기.
         */

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String generatedToken = Jwts.builder()
                .setSubject(email)
                .claim("auth", authorities)
                .setExpiration(tokenExpireIn())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return AuthorizationDto.TokenResponse.builder()
                .accessToken(TOKEN_GRANT_TYPE)
                .accessToken(generatedToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        String email = claims.getSubject();

        if (claims.get("auth") == null) throw new IllegalArgumentException("Not Found Token");

        if(StringUtils.hasText(email)) {
            Member findMember = memberMapper.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Not Found Entity"));
            UserDetailsImpl principal = new UserDetailsImpl(findMember);
            return new UsernamePasswordAuthenticationToken(findMember, null, principal.getAuthorities());
        }

        return null;
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("",e);
        } catch (ExpiredJwtException e) {
            log.info("",e);
        } catch (UnsupportedJwtException e) {
            log.info("",e);
        } catch (IllegalArgumentException e) {
            log.info("",e);
        }

        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
