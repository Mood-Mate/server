package com.pado.socialdiary.api.moduel.member.service;

import com.pado.socialdiary.api.common.dto.AuthorizationDto;
import com.pado.socialdiary.api.config.security.JwtProvider;
import com.pado.socialdiary.api.constants.AttachPath;
import com.pado.socialdiary.api.constants.RefTable;
import com.pado.socialdiary.api.constants.ResourcePath;
import com.pado.socialdiary.api.exception.member.DuplicateEmailException;
import com.pado.socialdiary.api.exception.member.PasswordConfirmationException;
import com.pado.socialdiary.api.moduel.follow.entity.Follow;
import com.pado.socialdiary.api.moduel.member.dto.*;
import com.pado.socialdiary.api.moduel.member.entity.LoginProvider;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.entity.MemberHistory;
import com.pado.socialdiary.api.moduel.member.repository.MemberRepository;
import com.pado.socialdiary.api.utils.attach.dto.AttachDto;
import com.pado.socialdiary.api.utils.attach.entity.Attached;
import com.pado.socialdiary.api.utils.attach.repository.AttachedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.pado.socialdiary.api.constants.YesNoCode.Y;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final AttachedRepository attachedRepository;

    @Transactional
    public void join(MemberJoinRequest memberJoinRequest) {

        if(!memberJoinRequest.getPassword().equals(memberJoinRequest.getPasswordConfirm())) {
            throw new PasswordConfirmationException();
        }

        Integer findDuplicateEmailCount = memberRepository.findDuplicateEmailCount(memberJoinRequest.getEmail());

        if (findDuplicateEmailCount > 0) {
            throw new DuplicateEmailException();
        }

        Member builtMember = Member.builder()
                .loginProvider(LoginProvider.LOCAL)
                .email(memberJoinRequest.getEmail())
                .password(passwordEncoder.encode(memberJoinRequest.getPassword()))
                .name(memberJoinRequest.getName())
                .nickname(memberJoinRequest.getNickname())
                .dateOfBirth(dateTimeConvert(memberJoinRequest.getYear(), memberJoinRequest.getMonth(), memberJoinRequest.getDayOfMonth()))
                .gender(memberJoinRequest.getGender())
                .build();

        memberRepository.save(builtMember);
        memberRepository.updateRegOrUpdColumn(builtMember.getMemberId());

        Member getMember = memberRepository.getByMemberId(builtMember.getMemberId());
        memberRepository.saveHistory(new MemberHistory(getMember));
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
    public void update(MemberUpdateRequest request) {

        if (StringUtils.hasText(request.getYear()) && StringUtils.hasText(request.getMonth()) && StringUtils.hasText(request.getDayOfMonth())) {
            LocalDateTime convertedDateOfBirth = dateTimeConvert(Integer.parseInt(request.getYear()), Integer.parseInt(request.getMonth()), Integer.parseInt(request.getDayOfMonth()));
            request.setConvertedDateOfBirth(convertedDateOfBirth);
        }

        if (StringUtils.hasText(request.getPassword())) {
            if(!request.getPassword().equals(request.getPasswordConfirm())) {
                throw new PasswordConfirmationException();
            }

            String encodedPassword = passwordEncoder.encode(request.getPassword());
            request.setEncodedPassword(encodedPassword);
        }

        memberRepository.update(request);

        Member getMember = memberRepository.getByMemberId(request.getMemberId());

        memberRepository.saveHistory(new MemberHistory(getMember));
    }

    @Transactional
    public String updateMemberPicture(Member member, AttachDto.UploadRequest uploadRequest) {
        Attached builtMemberPicture = Attached.builder()
                .refTable(RefTable.TB_MEMBER.getValue())
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

        attachedRepository.deleteMemberPicture(member.getMemberId(), RefTable.TB_MEMBER.getValue(), AttachPath.MEMBER_PICTURE.getValue());
        attachedRepository.createAttached(builtMemberPicture);

        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest();
        memberUpdateRequest.setMemberId(member.getMemberId());
        memberUpdateRequest.setPicture(builtMemberPicture.getAttachedFilename());

        memberRepository.update(memberUpdateRequest);
        memberRepository.saveHistory(new MemberHistory(memberRepository.getByMemberId(memberUpdateRequest.getMemberId())));
        return ResourcePath.MEMBER_PICTURE.getResource(memberRepository.getByMemberId(member.getMemberId()).getPicture());
    }

    public List<MemberSearchResponse> searchMember(Member member, String keyword) {

        List<MemberSearchResponse> findMembers = memberRepository.findMemberByKeyword(keyword);
        List<Follow> findFollowMembers = memberRepository.findFollowMember(member.getMemberId());

        findMembers.forEach(mber -> {
            findFollowMembers.forEach(follow -> {

                if (mber.getMemberId().equals(follow.getFollowerMemberId())) {
                    mber.setFollowAt(Y);
                }

            });
        });

        return findMembers;
    }

    @Transactional
    public void deleteMember(Integer memberId) {
        memberRepository.deleteMemberHistory(memberId);
        memberRepository.deleteMember(memberId);
    }

    public MemberProfileResponse getProfile(Integer memberId) {
        MemberProfileResponse memberProfile = memberRepository.findMemberProfile(memberId);
        memberProfile.setFollowerCount(memberRepository.getFollowingCount(memberId));
        memberProfile.setFollowingCount(memberRepository.getFollowerCount(memberId));

        if (memberProfile.getLoginProvider() == LoginProvider.LOCAL) {
            memberProfile.setPicture(ResourcePath.MEMBER_PICTURE.getResource(memberProfile.getPicture()));
        }

        return memberProfile;
    }
}
