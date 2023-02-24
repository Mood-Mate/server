package com.pado.socialdiary.api.moduel.follow;

import com.pado.socialdiary.api.moduel.follow.dto.FollowRequest;
import com.pado.socialdiary.api.moduel.follow.dto.FollowResponse;
import com.pado.socialdiary.api.moduel.follow.service.FollowService;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowRestController {

  private final FollowService followService;

  @GetMapping("/follower")
  public ResponseEntity<List<FollowResponse>> followerList(@RequestParam("memberId") Integer memberId) {

    return ResponseEntity.ok(followService.findFollowerList(memberId));
  }

  @GetMapping("/followee")
  public ResponseEntity<List<FollowResponse>> followeeList(@RequestParam("memberId") Integer memberId) {

    return ResponseEntity.ok(followService.findFolloweeList(memberId));
  }

  @PatchMapping("")
  public ResponseEntity following(@AuthenticationPrincipal Member member,
                                  @RequestBody FollowRequest followRequest) {

    followService.createOrDeleteFollowing(member, followRequest);

    return ResponseEntity.ok()
        .build();
  }
}
