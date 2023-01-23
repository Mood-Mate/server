package com.pado.socialdiary.api.emoji.dto;

import com.pado.socialdiary.api.emoji.entity.EmojiType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SympathyRequest {

    private Integer memberId;
    private Integer diaryId;
    private EmojiType emojiType;
}
