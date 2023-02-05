package com.pado.socialdiary.api.diary.dto;

import com.pado.socialdiary.api.diary.entity.Diary;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DiaryResponse {

    private Integer diaryId;
    private Integer memberId;
    private String title;
    private String contents;
    private LocalDateTime regDt;

    private List<DiaryCommentResponse> comments = new ArrayList<>();

    public DiaryResponse(Diary diary) {
        this.diaryId = diary.getDiaryId();
        this.memberId = diary.getMemberId();
        this.title = diary.getTitle();
        this.contents = diary.getContents();
        this.regDt = diary.getRegDt();
    }
}
