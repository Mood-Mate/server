package com.pado.socialdiary.api.moduel.diary.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DiaryUpdateRequest {

  private Integer memberId;
  private Integer diaryId;
  private String title;
  private String contents;
  private Boolean secret;
}
