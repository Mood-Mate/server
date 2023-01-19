package com.pado.socialdiary.api.diary.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryHistory {

  private Integer diaryHistoryId;
  private Integer diaryId;
  private Integer memberId;
  private String title;
  private String contents;
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
    this.regId = diary.getRegId();
    this.regDt = diary.getRegDt();
    this.updId = diary.getUpdId();
    this.updDt = diary.getUpdDt();
  }
}
