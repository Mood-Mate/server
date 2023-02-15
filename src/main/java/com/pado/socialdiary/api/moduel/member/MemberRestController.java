package com.pado.socialdiary.api.moduel.member;

import com.pado.socialdiary.api.moduel.member.dto.MemberJoinRequest;
import com.pado.socialdiary.api.moduel.member.dto.MemberLoginRequest;
import com.pado.socialdiary.api.moduel.member.dto.MemberSearchResponse;
import com.pado.socialdiary.api.moduel.member.dto.MemberUpdateRequest;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.service.MemberService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PatchMapping("/introduce")
    public ResponseEntity updateIntroduce(@AuthenticationPrincipal Member member,
                                          @RequestBody String introduce) {

        memberService.updateMemberIntroduce(member, introduce);

        return ResponseEntity.ok()
                .build();
    }

    @GetMapping
    public ResponseEntity searchMember(@AuthenticationPrincipal Member member,
                                       @RequestParam("keyword") String keyword) {

        List<MemberSearchResponse> result = memberService.searchMember(member, keyword);

        return ResponseEntity.ok(result);
    }
}
