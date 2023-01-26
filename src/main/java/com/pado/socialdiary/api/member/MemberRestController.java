package com.pado.socialdiary.api.member;

import com.pado.socialdiary.api.member.dto.MemberJoinRequest;
import com.pado.socialdiary.api.member.dto.MemberLoginRequest;
import com.pado.socialdiary.api.member.dto.MemberUpdateRequest;
import com.pado.socialdiary.api.member.entity.Member;
import com.pado.socialdiary.api.member.service.MemberService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity join(@RequestBody @Parameter MemberJoinRequest memberJoinRequest) {

        memberService.join(memberJoinRequest);

        return ResponseEntity.ok()
                .build();
    }

    @PatchMapping
    public ResponseEntity update(@RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberService.update(memberUpdateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberLoginRequest memberLoginRequest) {
        return ResponseEntity.ok(memberService.login(memberLoginRequest));
    }

    @PostMapping("/auth")
    public ResponseEntity authCheck(@AuthenticationPrincipal Member member) {

        if (member == null) {
            throw new RuntimeException("Not Fount Principal");
        }

        return ResponseEntity.ok(member);
    }
}
