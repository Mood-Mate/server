package com.pado.socialdiary.api.moduel.member.service;

import com.pado.socialdiary.api.constants.AttachPath;
import com.pado.socialdiary.api.exception.member.DuplicateEmailException;
import com.pado.socialdiary.api.moduel.member.dto.MemberJoinRequest;
import com.pado.socialdiary.api.moduel.member.dto.MemberUpdateRequest;
import com.pado.socialdiary.api.moduel.member.entity.GenderType;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.repository.MemberRepository;
import com.pado.socialdiary.api.utils.attach.AttachUtil;
import com.pado.socialdiary.api.utils.attach.dto.AttachDto;
import com.pado.socialdiary.api.utils.attach.entity.Attached;
import com.pado.socialdiary.api.utils.attach.repository.AttachedRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.pado.socialdiary.api.moduel.member.entity.GenderType.ETC;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AttachUtil attachUtil;

    @Autowired
    AttachedRepository attachedRepository;

    private final String BEFORE_MEMBER_EMAIL = "before@test.com";
    private final String VERIFY_MEMBER_EMAIL = "real@test.com";

    @BeforeEach
    void before() {
        MemberJoinRequest request = new MemberJoinRequest();
        request.setEmail(BEFORE_MEMBER_EMAIL);
        request.setPassword("1234");
        request.setName("testName");
        request.setNickname("testNickname");
        request.setGender(ETC);
        request.setYear(2000);
        request.setMonth(1);
        request.setDayOfMonth(1);

        memberService.join(request);
    }

    @AfterEach
    void after() {
        if (memberRepository.findByEmail(BEFORE_MEMBER_EMAIL).isPresent()) {
            memberService.deleteMember(memberRepository.findByEmail(BEFORE_MEMBER_EMAIL).get().getMemberId());
        }

        if (memberRepository.findByEmail(VERIFY_MEMBER_EMAIL).isPresent()) {
            memberService.deleteMember(memberRepository.findByEmail(VERIFY_MEMBER_EMAIL).get().getMemberId());
        }
    }

    @Test
    @Transactional
    @DisplayName("회원가입 테스트")
    void join() {
        MemberJoinRequest request = new MemberJoinRequest();
        request.setEmail(VERIFY_MEMBER_EMAIL);
        request.setPassword("1234");
        request.setName("testName");
        request.setNickname("testNickname");
        request.setGender(ETC);
        request.setYear(2000);
        request.setMonth(1);
        request.setDayOfMonth(1);

        memberService.join(request);

        Member findMember = memberRepository.findByEmail(VERIFY_MEMBER_EMAIL).get();

        assertEquals(request.getEmail(), findMember.getEmail());
    }

    @Test
    @Transactional
    @DisplayName("회원가입 이메일 중복 예외 테스트")
    void joinExceptionTest() {
        //given
        MemberJoinRequest request = new MemberJoinRequest();
        request.setEmail(BEFORE_MEMBER_EMAIL);
        request.setPassword("1234");
        request.setName("testName");
        request.setNickname("testNickname");
        request.setGender(ETC);
        request.setYear(2000);
        request.setMonth(1);
        request.setDayOfMonth(1);

        // when -> then(verify exception)
        assertThrows(DuplicateEmailException.class, () -> memberService.join(request));
    }

    @Test
    @Transactional
    @DisplayName("회원 수정 테스트")
    void memberUpdateTest() {
        Member findMember = memberRepository.findByEmail(BEFORE_MEMBER_EMAIL).get();

        MemberUpdateRequest request = new MemberUpdateRequest();
        request.setMemberId(findMember.getMemberId());
        request.setPassword("1234");
        request.setName("updateName");
        request.setNickname("updateNickname");
        request.setIntroduce("updateIntroduce");
        request.setGender(GenderType.MAN.getValue());
        request.setYear("2022");
        request.setMonth("06");
        request.setDayOfMonth("05");

        memberService.update(request);

        Member updatedMember = memberRepository.findByEmail(BEFORE_MEMBER_EMAIL).get();

        assertEquals(updatedMember.getPassword(), request.getEncodedPassword());
        assertEquals(updatedMember.getName(), request.getName());
        assertEquals(updatedMember.getNickname(), request.getNickname());
        assertEquals(updatedMember.getIntroduce(), request.getIntroduce());
        assertEquals(updatedMember.getGender().getValue(), request.getGender());
        assertEquals(updatedMember.getDateOfBirth(), request.getConvertedDateOfBirth());
    }

    @Test
    @Transactional
    @DisplayName("회원 프로필사진 업데이트")
    void updateMemberPictureTest() {
        //given
        final String ATTACH_FILENAME = "picture.jpg";
        String attachedFilename = attachUtil.createAttachedFilename(ATTACH_FILENAME);
        AttachDto.UploadRequest uploadRequest = new AttachDto.UploadRequest(ATTACH_FILENAME, attachedFilename, AttachPath.MEMBER_PICTURE.getValue(), 5L);
        Member findMember = memberRepository.findByEmail(BEFORE_MEMBER_EMAIL).get();

        //when
        memberService.updateMemberPicture(findMember, uploadRequest);

        Member updatedPictureMember = memberRepository.findByEmail(BEFORE_MEMBER_EMAIL).get();
        Attached findAttached = attachedRepository.findMemberPictureByMemberId(updatedPictureMember.getMemberId());

        //then
        assertEquals(attachedFilename, updatedPictureMember.getPicture());

        assertEquals(ATTACH_FILENAME, findAttached.getOriginalFilename());
        assertEquals(attachedFilename, findAttached.getAttachedFilename());
        assertEquals(uploadRequest.getAttachedPath(), findAttached.getAttachedPath());
    }

    @Test
    @Transactional
    @DisplayName("회원 삭제")
    void deleteMemberTest() {
        //given
        Member findMember = memberRepository.findByEmail(BEFORE_MEMBER_EMAIL).get();

        //when
        memberService.deleteMember(findMember.getMemberId());
        Optional<Member> deletedMember = memberRepository.findByEmail(BEFORE_MEMBER_EMAIL);

        //then
        assertEquals(deletedMember, Optional.empty());
        assertThrows(NoSuchElementException.class, () -> deletedMember.get());
    }
}
