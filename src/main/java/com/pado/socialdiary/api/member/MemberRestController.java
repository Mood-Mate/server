package com.pado.socialdiary.api.member;

import com.pado.socialdiary.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity test() {
        return ResponseEntity.ok(memberService.testOne());
    }
}
