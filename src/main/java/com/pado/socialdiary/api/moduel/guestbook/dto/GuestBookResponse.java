package com.pado.socialdiary.api.moduel.guestbook.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GuestBookResponse {

	private Integer guestBookId;
	private Integer hostMemberId;
	private Integer guestMemberId;
	private String contents;
	private LocalDateTime regDt;
	private String nickname;

	public GuestBookResponse(Integer guestBookId, Integer hostMemberId, Integer guestMemberId, String contents, LocalDateTime regDt, String nickname) {
		this.guestBookId = guestBookId;
		this.hostMemberId = hostMemberId;
		this.guestMemberId = guestMemberId;
		this.contents = contents;
		this.regDt = regDt;
		this.nickname = nickname;
	}
}
