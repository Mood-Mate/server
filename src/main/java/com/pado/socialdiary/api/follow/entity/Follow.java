package com.pado.socialdiary.api.follow.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Follow {

  private Integer followId;
  private Integer followerMemberId;
  private Integer followeeMemberId;
  private Integer regId;
  private LocalDateTime regDt;
  private Integer updId;
  private LocalDateTime updDt;

  @Builder
  public Follow(Integer followerMemberId, Integer followeeMemberId) {
    this.followerMemberId = followerMemberId;
    this.followeeMemberId = followeeMemberId;
    this.regId = followerMemberId;
    this.regDt = LocalDateTime.now();
    this.updId = followerMemberId;
    this.updDt = LocalDateTime.now();
  }
}
