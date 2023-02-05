package com.pado.socialdiary.api.diary.dto;

import com.pado.socialdiary.api.common.constants.YesNoCode;
import com.pado.socialdiary.api.diary.entity.DiaryComment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DiaryCommentResponse {

    private Integer diaryCommentId;
    private Integer diaryId;
    private Integer memberId;
    private String contents;
    private YesNoCode delAt;
    private LocalDateTime regDt;

    public DiaryCommentResponse(DiaryComment diaryComment) {
        this.diaryCommentId = diaryComment.getDiaryCommentId();
        this.diaryId = diaryComment.getDiaryId();
        this.memberId = diaryComment.getMemberId();
        this.contents = diaryComment.getContents();
        this.delAt = diaryComment.getDelAt();
        this.regDt = diaryComment.getRegDt();
    }
}
