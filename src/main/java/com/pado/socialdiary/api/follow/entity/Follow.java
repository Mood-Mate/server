package com.pado.socialdiary.api.follow.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Follow {

  private Integer followId;
  private Integer followerMemberId;
  private Integer followeeMemberId;

  @Builder
  public Follow(Integer followerMemberId, Integer followeeMemberId) {
    this.followerMemberId = followerMemberId;
    this.followeeMemberId = followeeMemberId;
  }
}
