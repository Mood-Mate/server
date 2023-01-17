package com.pado.socialdiary.api.member.service;

import com.pado.socialdiary.api.common.config.security.JwtProvider;
import com.pado.socialdiary.api.common.config.security.oauth.OAuthAttributes;
import com.pado.socialdiary.api.common.dto.response.TokenResponse;
import com.pado.socialdiary.api.member.dto.MemberJoinRequest;
import com.pado.socialdiary.api.member.dto.MemberLoginRequest;
import com.pado.socialdiary.api.member.dto.MemberUpdateRequest;
import com.pado.socialdiary.api.member.entity.Member;
import com.pado.socialdiary.api.member.entity.MemberHistory;
import com.pado.socialdiary.api.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void join(MemberJoinRequest memberJoinRequest) {

        Integer findDuplicateEmailCount = memberMapper.findDuplicateEmailCount(memberJoinRequest.getEmail());

        if (findDuplicateEmailCount > 0) {
            throw new IllegalArgumentException("Duplicate Email");
        }

        Member builtMember = Member.builder()
                .email(memberJoinRequest.getEmail())
                .password(passwordEncoder.encode(memberJoinRequest.getPassword()))
                .name(memberJoinRequest.getName())
                .nickname(memberJoinRequest.getNickname())
                .dateOfBirth(dateTimeConvert(memberJoinRequest.getYear(), memberJoinRequest.getMonth(), memberJoinRequest.getDayOfMonth()))
                .gender(memberJoinRequest.getGender())
                .build();

        memberMapper.save(builtMember);
        memberMapper.updateRegOrUpdColumn(builtMember.getMemberId());

        Member getMember = memberMapper.getByMemberId(builtMember.getMemberId());
        memberMapper.saveHistory(new MemberHistory(getMember));
    }

    private LocalDateTime dateTimeConvert(Integer year, Integer month, Integer dayOfMonth) {
        return LocalDateTime.of(year, month, dayOfMonth, 0, 0);
    }

    public TokenResponse login(MemberLoginRequest request) {

        System.out.println("memberService login");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        TokenResponse tokenResponse = jwtProvider.generateToken(authenticate);

        return tokenResponse;
    }

    @Transactional
    public void update(MemberUpdateRequest memberUpdateRequest) {
        memberMapper.update(memberUpdateRequest);
        Member getMember = memberMapper.getByMemberId(memberUpdateRequest.getMemberId());

        memberMapper.saveHistory(new MemberHistory(getMember));
    }
}
