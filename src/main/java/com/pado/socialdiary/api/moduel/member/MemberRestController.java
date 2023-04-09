package com.pado.socialdiary.api.moduel.member;

import com.pado.socialdiary.api.constants.AttachPath;
import com.pado.socialdiary.api.moduel.member.dto.MemberJoinRequest;
import com.pado.socialdiary.api.moduel.member.dto.MemberLoginRequest;
import com.pado.socialdiary.api.moduel.member.dto.MemberSearchResponse;
import com.pado.socialdiary.api.moduel.member.dto.MemberUpdateRequest;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.service.MemberService;
import com.pado.socialdiary.api.utils.attach.AttachUtil;
import com.pado.socialdiary.api.utils.attach.dto.AttachDto;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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
    private final AttachUtil attachUtil;

    @PostMapping
    public ResponseEntity join(@Validated @RequestBody @Parameter MemberJoinRequest memberJoinRequest) {
        memberService.join(memberJoinRequest);
        return ResponseEntity.ok()
                .build();
    }

    @PatchMapping
    public ResponseEntity update(@Validated @RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberService.update(memberUpdateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody MemberLoginRequest memberLoginRequest) {
        return ResponseEntity.ok(memberService.login(memberLoginRequest));
    }

    @GetMapping("/{memberId}/profile")
    public ResponseEntity profile(@PathVariable("memberId") Integer memberId) {
        return ResponseEntity.ok(memberService.getProfile(memberId));
    }

    @PatchMapping("/picture")
    public ResponseEntity updatePicture(@AuthenticationPrincipal Member member,
                                        @RequestPart MultipartFile multipartFile) throws IOException {
        AttachDto.UploadRequest uploadRequest = attachUtil.attachedFile(AttachPath.MEMBER_PICTURE.getValue(), multipartFile);
        return ResponseEntity.ok(memberService.updateMemberPicture(member, uploadRequest));
    }

    @GetMapping("search")
    public ResponseEntity searchMember(@Validated @AuthenticationPrincipal Member member,
                                       @RequestParam("keyword") String keyword) {
        List<MemberSearchResponse> result = memberService.searchMember(member, keyword);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/auth")
    public ResponseEntity authCheck(@Validated @AuthenticationPrincipal Member member) {

        if (member == null) {
            throw new RuntimeException("Not Fount Principal");
        }

        return ResponseEntity.ok(member);
    }
}
