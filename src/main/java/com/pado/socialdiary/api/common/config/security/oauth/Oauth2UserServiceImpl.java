package com.pado.socialdiary.api.common.config.security.oauth;

import com.pado.socialdiary.api.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Oauth2UserServiceImpl {

    public Object ofKakao(String code) throws URISyntaxException, MalformedURLException {

        RestTemplate restTemplate = new RestTemplate();

        String requestTokenUrl = "https://kauth.kakao.com/oauth/token";
        requestTokenUrl += "?client_id=b7e4a310abc54328e29fd9a6a2ff066c";
        requestTokenUrl += "&redirect_uri=http://localhost:8080/api/oauth/kakao";
        requestTokenUrl += "&code=" + code;
        requestTokenUrl += "&grant_type=authorization_code";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> httpEntity = new HttpEntity<>(code, headers);

        ResponseEntity<HashMap> result = restTemplate.exchange(requestTokenUrl, HttpMethod.POST, httpEntity, HashMap.class);


        HttpHeaders authHeader = new HttpHeaders();
        authHeader.add("Authorization", memberInfoKakao(result.getBody()));
        RestTemplate authRestTemplate = new RestTemplate();
        String requestUserInfo = "https://kapi.kakao.com/v2/user/me";

        String[] requestProps = {"kakao_account.email", "name"};

        return authRestTemplate.exchange(requestUserInfo, HttpMethod.GET, new HttpEntity<>(requestProps, authHeader), HashMap.class);
    }

    public String memberInfoKakao(Map<String, Object> params) {

        String accessToken = (String) params.get("access_token");
        String tokenType = (String) params.get("token_type");

        return tokenType + " " + accessToken;
    }
}
