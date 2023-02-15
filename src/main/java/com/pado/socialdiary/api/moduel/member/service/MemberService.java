package com.pado.socialdiary.api.moduel.member.service;

import com.pado.socialdiary.api.common.dto.AuthorizationDto;
import com.pado.socialdiary.api.utils.attach.AttachUtil;
import com.pado.socialdiary.api.utils.attach.dto.AttachDto;
import com.pado.socialdiary.api.utils.attach.entity.Attached;
import com.pado.socialdiary.api.utils.attach.mapper.AttachedMapper;
import com.pado.socialdiary.api.config.security.JwtProvider;
import com.pado.socialdiary.api.constants.AttachPath;
import com.pado.socialdiary.api.constants.RefTable;
import com.pado.socialdiary.api.moduel.follow.entity.Follow;
import com.pado.socialdiary.api.moduel.member.dto.MemberJoinRequest;
import com.pado.socialdiary.api.moduel.member.dto.MemberLoginRequest;
import com.pado.socialdiary.api.moduel.member.dto.MemberSearchResponse;
import com.pado.socialdiary.api.moduel.member.dto.MemberUpdateRequest;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.entity.MemberHistory;
import com.pado.socialdiary.api.moduel.member.mapper.MemberMapper;
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
import java.util.List;

import static com.pado.socialdiary.api.constants.YesNoCode.Y;

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

    public AuthorizationDto.TokenResponse login(MemberLoginRequest request) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        AuthorizationDto.TokenResponse tokenResponse = jwtProvider.generateToken(authenticate);

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

    public List<MemberSearchResponse> searchMember(Member member, String keyword) {

        List<MemberSearchResponse> findMembers = memberMapper.findMemberByKeyword(keyword);
        List<Follow> findFollowMembers = memberMapper.findFollowMember(member.getMemberId());

        findMembers.forEach(mber -> {
            findFollowMembers.forEach(follow -> {

                if (mber.getMemberId().equals(follow.getFolloweeMemberId())) {
                    mber.setFollowAt(Y);
                }

            });
        });

        return findMembers;
    }
}
