package com.pado.socialdiary.api.emoji.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Emoji {

    private Integer diaryEmojiId;
    private Integer memberId;
    private Integer diaryId;
    private EmojiType emoji;

    @Builder
    public Emoji(Integer memberId, Integer diaryId, EmojiType emoji) {
        this.memberId = memberId;
        this.diaryId = diaryId;
        this.emoji = emoji;
    }
}
