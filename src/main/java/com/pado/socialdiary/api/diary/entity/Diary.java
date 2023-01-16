package com.pado.socialdiary.api.diary.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Diary {

    private Integer diaryId;
    private Integer memberId;
    private String title;
    private String contents;
    private Integer regId;
    private LocalDateTime regDt;
    private Integer updId;
    private LocalDateTime updDt;

    @Builder
    public Diary(Integer diaryId, Integer memberId, String title, String contents) {
        this.diaryId = diaryId;
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;

        this.regDt = LocalDateTime.now();
        this.updDt = LocalDateTime.now();
    }
}
