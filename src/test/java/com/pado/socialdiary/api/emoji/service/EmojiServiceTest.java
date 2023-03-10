package com.pado.socialdiary.api.emoji.service;

import com.pado.socialdiary.api.moduel.diary.entity.Diary;
import com.pado.socialdiary.api.moduel.diary.service.DiaryService;
import com.pado.socialdiary.api.moduel.emoji.dto.SympathyRequest;
import com.pado.socialdiary.api.moduel.emoji.entity.EmojiType;
import com.pado.socialdiary.api.moduel.emoji.mapper.EmojiMapper;
import com.pado.socialdiary.api.moduel.emoji.service.EmojiService;
import com.pado.socialdiary.api.moduel.member.dto.MemberJoinRequest;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.repository.MemberRepository;
import com.pado.socialdiary.api.moduel.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.NoSuchElementException;

import static com.pado.socialdiary.api.moduel.member.entity.GenderType.ETC;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmojiServiceTest {

    @Autowired
    EmojiService emojiService;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    DiaryService diaryService;

    @Autowired
    EmojiMapper emojiMapper;

    private final String BEFORE_MEMBER_EMAIL = "before@test.com";

    @BeforeEach
    void before() throws IOException {
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

    @Test
    @Transactional
    void createSympathyTest() {

//        given
        Member findMember = memberRepository.findByEmail(BEFORE_MEMBER_EMAIL).get();

        Diary mockDiary = Mockito.mock(Diary.class);
        Mockito.when(mockDiary.getDiaryId()).thenReturn(111);

        SympathyRequest sympathyRequest = new SympathyRequest();
        sympathyRequest.setMemberId(findMember.getMemberId());
        sympathyRequest.setDiaryId(mockDiary.getDiaryId());
        sympathyRequest.setEmojiType(EmojiType.LOVE);

//        when
        emojiService.createOrDeleteSympathy(findMember, sympathyRequest);
        Integer existSympathy = emojiMapper.existSympathy(findMember.getMemberId(), mockDiary.getDiaryId()).get();

//        then
        assertNotNull(existSympathy);
    }

    @Test
    @Transactional
    void deleteSympathyTest() {

//        given
        Member findMember = memberRepository.findByEmail(BEFORE_MEMBER_EMAIL).get();
        Diary mockDiary = Mockito.mock(Diary.class);
        Mockito.when(mockDiary.getDiaryId()).thenReturn(111);

        SympathyRequest sympathyRequest = new SympathyRequest();
        sympathyRequest.setMemberId(findMember.getMemberId());
        sympathyRequest.setDiaryId(mockDiary.getDiaryId());
        sympathyRequest.setEmojiType(EmojiType.LOVE);

//        when
//        공감 클릭 1번 -> 공감 생성
        emojiService.createOrDeleteSympathy(findMember, sympathyRequest);
        Integer createdSympathy = emojiMapper.existSympathy(findMember.getMemberId(), mockDiary.getDiaryId()).get();

//        공감 클릭 1번 더 -> 공감 삭제
        emojiService.createOrDeleteSympathy(findMember, sympathyRequest);

//        then -> Not null Check, Optional<T>.get() exception Check
        assertNotNull(createdSympathy);
        assertThrows(NoSuchElementException.class, () -> emojiMapper.existSympathy(findMember.getMemberId(), mockDiary.getDiaryId()).get());
    }
}
