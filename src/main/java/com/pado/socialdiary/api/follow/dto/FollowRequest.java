package com.pado.socialdiary.api.follow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowRequest {

  private Integer followerMemberId;
  private Integer followeeMemberId;

}
