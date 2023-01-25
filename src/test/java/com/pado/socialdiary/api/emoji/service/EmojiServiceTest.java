package com.pado.socialdiary.api.emoji.service;

import com.pado.socialdiary.api.emoji.dto.SympathyRequest;
import com.pado.socialdiary.api.emoji.entity.EmojiType;
import com.pado.socialdiary.api.emoji.mapper.EmojiMapper;
import com.pado.socialdiary.api.member.entity.Member;
import com.pado.socialdiary.api.member.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class EmojiServiceTest {

    @Autowired
    EmojiMapper emojiMapper;

    @MockBean
    MemberMapper memberMapper;

    @Test
    @Transactional
    void createSympathy() {
        Integer tmpDiaryId = 2;

        Member member = mock(Member.class);
        when(member.getMemberId()).thenReturn(1);

        SympathyRequest request = new SympathyRequest();
        request.setMemberId(member.getMemberId());
        request.setDiaryId(tmpDiaryId);
        request.setEmojiType(EmojiType.좋아요);

        emojiMapper.createSympathy(request);
        Optional<Integer> existSympathy = emojiMapper.existSympathy(request.getMemberId(), request.getDiaryId());

        if(existSympathy.isEmpty()) {
            emojiMapper.createSympathy(request);
        } else {
            emojiMapper.deleteSympathy(request);
        }
    }
}
