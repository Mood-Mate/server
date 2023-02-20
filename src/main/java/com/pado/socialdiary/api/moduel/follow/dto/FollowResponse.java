package com.pado.socialdiary.api.moduel.follow.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FollowResponse {

	private Integer followId;
	private Integer followerMemberId;
	private Integer followeeMemberId;
	private LocalDateTime regDt;
	private String nickname;
	private String picture;

	public FollowResponse(Integer followId, Integer followerMemberId, Integer followeeMemberId,
		LocalDateTime regDt, String nickname, String picture) {
		this.followId = followId;
		this.followerMemberId = followerMemberId;
		this.followeeMemberId = followeeMemberId;
		this.regDt = regDt;
		this.nickname = nickname;
		this.picture = picture;
	}
}
