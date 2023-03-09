package com.pado.socialdiary.api.moduel.follow.service;

import com.pado.socialdiary.api.moduel.follow.dto.FollowRequest;
import com.pado.socialdiary.api.moduel.follow.dto.FollowResponse;
import com.pado.socialdiary.api.moduel.follow.mapper.FollowMapper;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

  private final FollowMapper followMapper;

  public List<FollowResponse> findFollowerList(Integer memberId) {

    return followMapper.findFollow(memberId, null);
  }

  public List<FollowResponse> findFollowingList(Integer memberId) {

    return followMapper.findFollow(null, memberId);
  }

  @Transactional
  public void createOrDeleteFollowing(Member member, FollowRequest followRequest) {

    followRequest.setFollowerMemberId(member.getMemberId());
    Optional<Integer> existFollowing = followMapper.existFollowing(followRequest);

    if(existFollowing.isEmpty()) {
      followMapper.createFollowing(followRequest);
    } else {
      followMapper.deleteFollowing(followRequest);
    }
  }
}
