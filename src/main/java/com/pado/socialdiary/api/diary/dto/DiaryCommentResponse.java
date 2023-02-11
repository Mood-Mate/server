package com.pado.socialdiary.api.diary.dto;

import com.pado.socialdiary.api.common.constants.YesNoCode;
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
    private String contents;
    private YesNoCode delAt;
    private LocalDateTime regDt;
}
