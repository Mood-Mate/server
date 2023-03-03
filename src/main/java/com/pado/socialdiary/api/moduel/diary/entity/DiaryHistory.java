package com.pado.socialdiary.api.moduel.diary.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

@Getter
@NoArgsConstructor
public class DiaryHistory {

  @Id
  private Integer diaryHistoryId;
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
  public DiaryHistory(Diary diary) {
    this.diaryId = diary.getDiaryId();
    this.memberId = diary.getMemberId();
    this.title = diary.getTitle();
    this.contents = diary.getContents();
    this.secret = diary.getSecret();
    this.regId = diary.getRegId();
    this.regDt = diary.getRegDt();
    this.updId = diary.getUpdId();
    this.updDt = diary.getUpdDt();
  }
}
