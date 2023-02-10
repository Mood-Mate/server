package com.pado.socialdiary.api.common.pageable.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursorPageable {

  public static final Integer DEFAULT_PAGE_SIZE = 10;

  private Integer cursor;
  private Integer pageSize = DEFAULT_PAGE_SIZE;

  public CursorPageable(Integer pageSize) { this.pageSize = pageSize; }
}
