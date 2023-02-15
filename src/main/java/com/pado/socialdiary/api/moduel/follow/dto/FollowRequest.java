package com.pado.socialdiary.api.moduel.follow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowRequest {

  private Integer followerMemberId;
  private Integer followeeMemberId;

}
