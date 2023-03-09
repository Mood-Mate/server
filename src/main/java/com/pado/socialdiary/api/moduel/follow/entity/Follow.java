package com.pado.socialdiary.api.moduel.follow.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Follow {

  private Integer followId;
  private Integer followerMemberId;
  private Integer followingMemberId;
  private Integer regId;
  private LocalDateTime regDt;
  private Integer updId;
  private LocalDateTime updDt;

  @Builder
  public Follow(Integer followerMemberId, Integer followingMemberId) {
    this.followerMemberId = followerMemberId;
    this.followingMemberId = followingMemberId;
    this.regId = followerMemberId;
    this.regDt = LocalDateTime.now();
    this.updId = followerMemberId;
    this.updDt = LocalDateTime.now();
  }
}
