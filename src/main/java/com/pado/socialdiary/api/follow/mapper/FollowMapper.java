package com.pado.socialdiary.api.follow.mapper;

import com.pado.socialdiary.api.follow.dto.FollowRequest;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowMapper {

  Optional<Integer> existFollowing(FollowRequest followRequest);
  void createFollowing(FollowRequest followRequest);
  void deleteFollowing(FollowRequest followRequest);
  List<Integer> findFollowee(Integer memberId);
}
