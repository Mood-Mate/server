package com.pado.socialdiary.api.common.pageable.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CursorPageResponse<T> {

  private List<T> data;
  private Integer next;
}
