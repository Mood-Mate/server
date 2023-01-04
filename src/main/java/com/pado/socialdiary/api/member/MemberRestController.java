package com.pado.socialdiary.api.member;

import com.pado.socialdiary.api.member.dto.MemberJoinRequest;
import com.pado.socialdiary.api.member.dto.MemberLoginRequest;
import com.pado.socialdiary.api.member.entity.Member;
import com.pado.socialdiary.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity join(MemberJoinRequest memberJoinRequest) {

        memberService.join(memberJoinRequest);

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity login(MemberLoginRequest memberLoginRequest) {
        return ResponseEntity.ok(memberService.login(memberLoginRequest));
    }

    @PostMapping("/auth")
    public ResponseEntity authCheck(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(member);
    }
}
