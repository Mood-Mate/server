package com.pado.socialdiary.api.moduel.emoji.dto;

import com.pado.socialdiary.api.moduel.emoji.entity.EmojiType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SympathyResponse {

    private Integer memberId;
    private Integer diaryId;
    private EmojiType emojiType;

    public SympathyResponse(Integer memberId, Integer diaryId, EmojiType emojiType) {
        this.memberId = memberId;
        this.diaryId = diaryId;
        this.emojiType = emojiType;
    }
}
