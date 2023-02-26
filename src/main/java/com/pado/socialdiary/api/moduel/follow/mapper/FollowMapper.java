package com.pado.socialdiary.api.moduel.follow.mapper;

import com.pado.socialdiary.api.moduel.follow.dto.FollowRequest;
import com.pado.socialdiary.api.moduel.follow.dto.FollowResponse;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowMapper {

  Optional<Integer> existFollowing(FollowRequest followRequest);
  void createFollowing(FollowRequest followRequest);
  void deleteFollowing(FollowRequest followRequest);
  List<Integer> findFolloweeId(Integer memberId);
  List<FollowResponse> findFollow(@Param("followerMemberId") Integer followerMemberId,  @Param("followeeMemberId") Integer followeeMemberId);
}
