package com.pado.socialdiary.api.moduel.follow.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FollowResponse {

	private Integer followId;
	private Integer followerMemberId;
	private Integer followingMemberId;
	private LocalDateTime regDt;
	private String nickname;
	private String picture;

	public FollowResponse(Integer followId, Integer followerMemberId, Integer followingMemberId,
		LocalDateTime regDt, String nickname, String picture) {
		this.followId = followId;
		this.followerMemberId = followerMemberId;
		this.followingMemberId = followingMemberId;
		this.regDt = regDt;
		this.nickname = nickname;
		this.picture = picture;
	}
}
