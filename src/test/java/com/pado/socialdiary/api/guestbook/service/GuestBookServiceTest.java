package com.pado.socialdiary.api.guestbook.service;

import com.pado.socialdiary.api.moduel.guestbook.dto.GuestBookResponse;
import com.pado.socialdiary.api.moduel.guestbook.mapper.GuestBookMapper;
import com.pado.socialdiary.api.moduel.guestbook.service.GuestBookService;
import com.pado.socialdiary.api.moduel.member.dto.MemberJoinRequest;
import com.pado.socialdiary.api.moduel.member.entity.GenderType;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.repository.MemberRepository;
import com.pado.socialdiary.api.moduel.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class GuestBookServiceTest {

    @Autowired
    GuestBookService guestBookService;
    @Autowired
    GuestBookMapper guestBookMapper;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    private final String expectedValue01 = "test01";
    private final String expectedValue02 = "test02";
    private final String expectedEmailValue01 = "email01@email.com";
    private final String expectedEmailValue02 = "email02@email.com";

    @BeforeEach
    void createHostMember() {

        MemberJoinRequest memberJoinRequest = new MemberJoinRequest();
        memberJoinRequest.setEmail(expectedEmailValue01);
        memberJoinRequest.setPassword(expectedValue01);
        memberJoinRequest.setName(expectedValue01);
        memberJoinRequest.setNickname(expectedValue01);
        memberJoinRequest.setGender(GenderType.ETC);
        memberJoinRequest.setYear(2000);
        memberJoinRequest.setMonth(1);
        memberJoinRequest.setDayOfMonth(1);

        memberService.join(memberJoinRequest);

        memberJoinRequest.setEmail(expectedEmailValue02);
        memberJoinRequest.setPassword(expectedValue02);
        memberJoinRequest.setName(expectedValue02);
        memberJoinRequest.setNickname(expectedValue02);
        memberJoinRequest.setGender(GenderType.ETC);
        memberJoinRequest.setYear(2000);
        memberJoinRequest.setMonth(1);
        memberJoinRequest.setDayOfMonth(1);

        memberService.join(memberJoinRequest);
    }

    @Test
    @Transactional
    @DisplayName("방명록 업로드")
    void createGuestBook() {
        //given
        Member hostMember = memberRepository.findByEmail(expectedEmailValue01).get();
        Member guestMember = memberRepository.findByEmail(expectedEmailValue02).get();

        String contents = expectedValue01;

        //when
        guestBookService.createGuestBook(hostMember.getMemberId(), guestMember, contents);

        //then
        GuestBookResponse guestBook = guestBookMapper.findGuestBook(null, hostMember.getMemberId()).get(0);
        assertThat(guestBook.getContents()).isEqualTo(expectedValue01);
        assertThat(guestBook.getNickname()).isEqualTo(expectedValue02);
    }

    @Test
    @Transactional
    @DisplayName("방명록 삭제")
    void deleteGuestBook() {
        //given
        Member hostMember = memberRepository.findByEmail(expectedEmailValue01).get();
        Member guestMember = memberRepository.findByEmail(expectedEmailValue02).get();

        String contents = expectedValue01;

        //when
        guestBookService.createGuestBook(hostMember.getMemberId(), guestMember, contents);
        GuestBookResponse guestBook = guestBookMapper.findGuestBook(null, hostMember.getMemberId()).get(0);

        guestBookService.deleteGuestBook(guestBook.getGuestBookId());

        //then
        assertThat(guestBookMapper.findGuestBook(guestBook.getGuestBookId(), null)).isEmpty();
    }
}
