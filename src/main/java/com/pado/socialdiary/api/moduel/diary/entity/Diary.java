package com.pado.socialdiary.api.moduel.diary.entity;

import lombok.*;

import java.time.LocalDateTime;
import nonapi.io.github.classgraph.json.Id;

@Data
@NoArgsConstructor
public class Diary {

    @Id
    private Integer diaryId;
    private Integer memberId;
    private String title;
    private String contents;
    private Boolean secret;
    private Integer regId;
    private LocalDateTime regDt;
    private Integer updId;
    private LocalDateTime updDt;

    @Builder
    public Diary(Integer diaryId, Integer memberId, String title, String contents, Boolean secret) {
        this.diaryId = diaryId;
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.secret = secret;

        this.regDt = LocalDateTime.now();
        this.updDt = LocalDateTime.now();
    }
}
