package com.pado.socialdiary.api.common;

import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommonTestController {

    private final MemberMapper memberMapper;

    @GetMapping("/view")
    public String viewImage(Model model) {
        Member findMember = memberMapper.findByEmail("string").get();
        model.addAttribute("member", findMember);
        return "File";
    }

    @PostMapping("/api/member/auth")
    public ResponseEntity authCheck(@AuthenticationPrincipal Member member) {

        if (member == null) {
            throw new RuntimeException("Not Fount Principal");
        }

        return ResponseEntity.ok(member);
    }
}
