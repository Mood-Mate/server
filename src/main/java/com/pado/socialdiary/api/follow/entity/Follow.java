package com.pado.socialdiary.api.follow.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Follow {

  private Integer followId;
  private Integer followerId;
  private Integer followeeId;

  @Builder
  public Follow(Integer followerId, Integer followeeId) {
    this.followerId = followerId;
    this.followeeId = followeeId;
  }
}
