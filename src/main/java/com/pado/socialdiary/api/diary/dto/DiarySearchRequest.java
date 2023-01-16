package com.pado.socialdiary.api.diary.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DiarySearchRequest {
  private Integer memberId;
  private String regDt;
}
