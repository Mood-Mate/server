package com.pado.socialdiary.api.moduel.diary.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DiaryCreateRequest {

  private Integer memberId;
  private String title;
  private String contents;

}
