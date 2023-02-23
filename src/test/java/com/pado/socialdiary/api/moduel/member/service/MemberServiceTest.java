package com.pado.socialdiary.api.moduel.member.service;

import com.pado.socialdiary.api.constants.AttachPath;
import com.pado.socialdiary.api.exception.member.DuplicateEmailException;
import com.pado.socialdiary.api.moduel.member.dto.MemberJoinRequest;
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
import org.springframework.transaction.annotation.Transactional;

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
    @DisplayName("회원 한줄소개 수정 테스트")
    void updateMemberIntroduceTest() {
        //given
        final String PARAM_INTRODUCE = "회원 한줄소개 입둥.";
        Member findMember = memberRepository.findByEmail(BEFORE_MEMBER_EMAIL).get();

        //when
        memberService.updateMemberIntroduce(findMember, PARAM_INTRODUCE);

        //then
        Member changedIntroduceMember = memberRepository.findByEmail(BEFORE_MEMBER_EMAIL).get();
        assertEquals(PARAM_INTRODUCE, changedIntroduceMember.getIntroduce());
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
