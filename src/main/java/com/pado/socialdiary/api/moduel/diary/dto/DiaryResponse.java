package com.pado.socialdiary.api.moduel.diary.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pado.socialdiary.api.moduel.member.entity.LoginProvider;

@Getter
@Setter
public class DiaryResponse {

    private Integer diaryId;
    private Integer memberId;
    private String title;
    private String contents;
    private Boolean secret;
    private String diaryPicture;
    private LocalDateTime regDt;
    private LocalDateTime updDt;
    private String nickname;
    private String picture;
    private LoginProvider loginProvider;

    private List<DiaryCommentResponse> comments = new ArrayList<>();

    public DiaryResponse(Integer diaryId, Integer memberId, String title, String contents, Boolean secret, String diaryPicture, LocalDateTime regDt, LocalDateTime updDt, String nickname, String picture, LoginProvider loginProvider) {
        this.diaryId = diaryId;
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.secret = secret;
        this.diaryPicture = diaryPicture;
        this.regDt = regDt;
        this.updDt = updDt;
        this.nickname = nickname;
        this.picture = picture;
        this.loginProvider = loginProvider;
    }
}
