package com.pado.socialdiary.api.moduel.diary.dto;

import com.pado.socialdiary.api.constants.YesNoCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DiaryCommentResponse {

    private Integer diaryCommentId;
    private Integer diaryId;

    private Integer memberId;
    private String nickname;
    private String memberPicture;

    private String contents;
    private LocalDateTime regDt;
}
