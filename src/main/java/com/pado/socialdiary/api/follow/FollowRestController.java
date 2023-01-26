package com.pado.socialdiary.api.follow;

import com.pado.socialdiary.api.follow.dto.FollowRequest;
import com.pado.socialdiary.api.follow.service.FollowService;
import com.pado.socialdiary.api.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowRestController {

  private final FollowService followService;

  @PatchMapping("")
  public ResponseEntity following(@AuthenticationPrincipal Member member,
                                  @RequestBody FollowRequest followRequest) {

    followService.createOrDeleteFollowing(member, followRequest);

    return ResponseEntity.ok()
        .build();
  }

}
