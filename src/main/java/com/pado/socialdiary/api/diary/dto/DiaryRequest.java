package com.pado.socialdiary.api.diary.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@Setter
@ToString
public class DiaryRequest {

  private Integer memberId;
  private String title;
  private String contents;

}
