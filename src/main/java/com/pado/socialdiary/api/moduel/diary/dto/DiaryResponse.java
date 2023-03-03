package com.pado.socialdiary.api.moduel.diary.dto;

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
    private Boolean secret;
    private LocalDateTime regDt;
    private LocalDateTime updDt;
    private String nickname;
    private String picture;

    private List<DiaryCommentResponse> comments = new ArrayList<>();

    public DiaryResponse(Integer diaryId, Integer memberId, String title, String contents, Boolean secret, LocalDateTime regDt, LocalDateTime updDt, String nickname, String picture) {
        this.diaryId = diaryId;
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.secret = secret;
        this.regDt = regDt;
        this.updDt = updDt;
        this.nickname = nickname;
        this.picture = picture;
    }
}
