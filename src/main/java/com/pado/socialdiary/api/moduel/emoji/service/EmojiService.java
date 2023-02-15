package com.pado.socialdiary.api.moduel.emoji.service;

import com.pado.socialdiary.api.moduel.emoji.dto.SympathyRequest;
import com.pado.socialdiary.api.moduel.emoji.mapper.EmojiMapper;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmojiService {

    private final EmojiMapper emojiMapper;

    @Transactional
    public void createOrDeleteSympathy(Member member, SympathyRequest request) {

        request.setMemberId(member.getMemberId());
        Optional<Integer> existSympathy = emojiMapper.existSympathy(request.getMemberId(), request.getDiaryId());

        if(existSympathy.isEmpty()) {
            emojiMapper.createSympathy(request);
        } else {
            emojiMapper.deleteSympathy(request);
        }

    }
}
