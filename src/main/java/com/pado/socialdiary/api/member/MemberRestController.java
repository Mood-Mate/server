package com.pado.socialdiary.api.member;

import com.pado.socialdiary.api.member.dto.MemberJoinRequest;
import com.pado.socialdiary.api.member.dto.MemberLoginRequest;
import com.pado.socialdiary.api.member.dto.MemberUpdateRequest;
import com.pado.socialdiary.api.member.entity.Member;
import com.pado.socialdiary.api.member.service.MemberService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PatchMapping("/picture")
    public ResponseEntity updatePicture(@AuthenticationPrincipal Member member,
                                        @RequestPart MultipartFile multipartFile) throws IOException {
        memberService.updateMemberPicture(member, multipartFile);
        return ResponseEntity.ok()
                .build();
    }
}
