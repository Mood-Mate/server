package com.pado.socialdiary.api.diary.entity;

import com.pado.socialdiary.api.common.constants.YesNoCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.pado.socialdiary.api.common.constants.YesNoCode.N;
import static com.pado.socialdiary.api.common.constants.YesNoCode.Y;

@Getter
@AllArgsConstructor
public class DiaryComment {

    private Integer diaryCommentId;
    private Integer diaryId;
    private Integer memberId;
    private String contents;
    private YesNoCode delAt;
    private Integer regId;
    private LocalDateTime regDt;
    private Integer updId;
    private LocalDateTime updDt;

    @Builder
    public DiaryComment(Integer diaryId, Integer memberId, String contents) {
        this.diaryId = diaryId;
        this.memberId = memberId;
        this.contents = contents;
        this.delAt = N;
        this.regId = memberId;
        this.regDt = LocalDateTime.now();
        this.updId = memberId;
        this.updDt = LocalDateTime.now();
    }

    public void delete() {
        this.delAt = Y;
    }

    public void updateBy(Integer memberId) {
        this.updId = memberId;
        this.updDt = LocalDateTime.now();
    }

    public boolean verifyCreator(Integer memberId) {
        return this.memberId.equals(memberId) ? true : false;
    }
}
