package com.pado.socialdiary.api.member.service;

import com.pado.socialdiary.api.common.config.security.JwtProvider;
import com.pado.socialdiary.api.common.config.security.oauth.OAuthAttributes;
import com.pado.socialdiary.api.member.entity.Member;
import com.pado.socialdiary.api.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberLoginService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberMapper memberMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);

//        DefaultOAuth2User defaultOAuth2User = new DefaultOAuth2User(Collections.singleton(
//                new SimpleGrantedAuthority(member.getRole().getRole())),
//                attributes.getAttributes(),
//                attributes.getNameAttributeKey()
//        );

        DefaultOAuth2User defaultOAuth2User = new DefaultOAuth2User(Collections.singleton(
                new SimpleGrantedAuthority(member.getRole().getRole())),
                attributes.toMap(),
                "email"
        );

        return defaultOAuth2User;
    }

    /**
     * 중복 체크 , 디비 저장(업데이트 된 회원프로필)
     */
    // TODO: 2023/01/15
    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = attributes.toEntity();
        memberMapper.join(member);

        return memberMapper.findByEmail(attributes.getEmail()).get();
    }
}
