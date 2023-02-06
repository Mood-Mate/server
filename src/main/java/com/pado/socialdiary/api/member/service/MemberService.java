package com.pado.socialdiary.api.member.service;

import com.pado.socialdiary.api.common.config.security.JwtProvider;
import com.pado.socialdiary.api.common.constants.AttachPath;
import com.pado.socialdiary.api.common.constants.RefTable;
import com.pado.socialdiary.api.common.dto.response.TokenResponse;
import com.pado.socialdiary.api.common.attach.AttachUtil;
import com.pado.socialdiary.api.common.attach.dto.AttachDto;
import com.pado.socialdiary.api.common.attach.entity.Attached;
import com.pado.socialdiary.api.common.attach.mapper.AttachedMapper;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final AttachUtil attachUtil;
    private final AttachedMapper attachedMapper;

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

    @Transactional
    public void updateMemberPicture(Member member, MultipartFile file) throws IOException {
        AttachDto.UploadRequest uploadRequest = attachUtil.attachedFile(AttachPath.MEMBER_PICTURE.getValue(), file);

        Attached builtMemberPicture = Attached.builder()
                .refTable(RefTable.HP_MEMBER.getValue())
                .refId(member.getMemberId())
                .originalFilename(uploadRequest.getOriginalFileName())
                .attachedFilename(uploadRequest.getAttachedFileName())
                .attachedPath(AttachPath.MEMBER_PICTURE.getValue())
                .fileSize(uploadRequest.getFileSize())
                .regId(member.getMemberId())
                .regDt(LocalDateTime.now())
                .updId(member.getMemberId())
                .regDt(LocalDateTime.now())
                .build();

        attachedMapper.createAttached(builtMemberPicture);

        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest();
        memberUpdateRequest.setMemberId(member.getMemberId());
        memberUpdateRequest.setPicture(builtMemberPicture.getOriginalFilename());

        memberMapper.update(memberUpdateRequest);
        memberMapper.saveHistory(new MemberHistory(memberMapper.getByMemberId(memberUpdateRequest.getMemberId())));
    }

    @Transactional
    public void updateMemberIntroduce(Member member, String introduce) {
        member.changeIntroduce(introduce);
        memberMapper.updateIntroduce(member.getMemberId(), introduce);
    }
}
