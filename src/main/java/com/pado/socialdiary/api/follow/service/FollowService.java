package com.pado.socialdiary.api.follow.service;

import com.pado.socialdiary.api.follow.dto.FollowRequest;
import com.pado.socialdiary.api.follow.mapper.FollowMapper;
import com.pado.socialdiary.api.member.entity.Member;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

  private final FollowMapper followMapper;

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

  public List<Integer> followList(Integer memberId) {

    return followMapper.findFollowee(memberId);
  }
}
