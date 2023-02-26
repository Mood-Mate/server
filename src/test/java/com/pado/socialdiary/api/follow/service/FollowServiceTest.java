package com.pado.socialdiary.api.follow.service;

import com.pado.socialdiary.api.moduel.follow.dto.FollowRequest;
import com.pado.socialdiary.api.moduel.follow.dto.FollowResponse;
import com.pado.socialdiary.api.moduel.follow.mapper.FollowMapper;
import com.pado.socialdiary.api.moduel.follow.service.FollowService;
import com.pado.socialdiary.api.moduel.member.dto.MemberJoinRequest;
import com.pado.socialdiary.api.moduel.member.entity.GenderType;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.repository.MemberRepository;
import com.pado.socialdiary.api.moduel.member.service.MemberService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class FollowServiceTest {

	@Autowired
	FollowService followService;
	@Autowired
	FollowMapper followMapper;
	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;

	private final String expectedValue01 = "test01";
	private final String expectedValue02 = "test02";
	private final String expectedEmailValue01 = "email01@email.com";
	private final String expectedEmailValue02 = "email02@email.com";

	@BeforeEach
	void createHostMember() {

		MemberJoinRequest memberJoinRequest = new MemberJoinRequest();
		memberJoinRequest.setEmail(expectedEmailValue01);
		memberJoinRequest.setPassword(expectedValue01);
		memberJoinRequest.setName(expectedValue01);
		memberJoinRequest.setNickname(expectedValue01);
		memberJoinRequest.setGender(GenderType.ETC);
		memberJoinRequest.setYear(2000);
		memberJoinRequest.setMonth(1);
		memberJoinRequest.setDayOfMonth(1);

		memberService.join(memberJoinRequest);

		memberJoinRequest.setEmail(expectedEmailValue02);
		memberJoinRequest.setPassword(expectedValue02);
		memberJoinRequest.setName(expectedValue02);
		memberJoinRequest.setNickname(expectedValue02);
		memberJoinRequest.setGender(GenderType.ETC);
		memberJoinRequest.setYear(2000);
		memberJoinRequest.setMonth(1);
		memberJoinRequest.setDayOfMonth(1);

		memberService.join(memberJoinRequest);
	}

	@Test
	@Transactional
	@DisplayName("팔로우 생성")
	void createFollow() {
		//given
		Member followerMember = memberRepository.findByEmail(expectedEmailValue01).get();
		Member followeeMember = memberRepository.findByEmail(expectedEmailValue02).get();

		FollowRequest followRequest = new FollowRequest();
		followRequest.setFolloweeMemberId(followeeMember.getMemberId());

		//when
		followService.createOrDeleteFollowing(followerMember, followRequest);

		//then
		FollowResponse followResponse = followMapper.findFollow(followeeMember.getMemberId(), null).get(0);
		assertThat(followResponse.getNickname()).isEqualTo(expectedValue01);
	}

	@Test
	@Transactional
	@DisplayName("팔로우 삭제")
	void deleteFollow() {
		//given
		Member followerMember = memberRepository.findByEmail(expectedEmailValue01).get();
		Member followeeMember = memberRepository.findByEmail(expectedEmailValue02).get();

		FollowRequest followRequest = new FollowRequest();
		followRequest.setFolloweeMemberId(followeeMember.getMemberId());

		//when
		followService.createOrDeleteFollowing(followerMember, followRequest);
		followService.createOrDeleteFollowing(followerMember, followRequest);

		//then
		assertThat(followMapper.findFollow(followeeMember.getMemberId(), null)).isEmpty();
		assertThat(followMapper.findFollow(null, followerMember.getMemberId())).isEmpty();
	}
}
