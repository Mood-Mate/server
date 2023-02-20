package com.pado.socialdiary.api.common;

import com.pado.socialdiary.api.constants.AttachPath;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.repository.MemberMapper;
import com.pado.socialdiary.api.moduel.member.service.MemberService;
import com.pado.socialdiary.api.utils.attach.AttachUtil;
import com.pado.socialdiary.api.utils.attach.dto.AttachDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CommonTestController {

    private final MemberMapper memberMapper;
    private final MemberService memberService;
    private final AttachUtil attachUtil;

    @GetMapping("/view")
    public String viewImage(Model model) {
        Member findMember = memberMapper.findByEmail("string").get();
        model.addAttribute("member", findMember);
        return "File";
    }

    @GetMapping("/upload")
    public String upload() {
        return "FileUploadTest";
    }

    @PostMapping("/upload")
    public String uploadPicture(@RequestParam Integer memberId,
                              @RequestPart("file") MultipartFile multipartFile) throws IOException {

        AttachDto.UploadRequest uploadRequest = attachUtil.attachedFile(AttachPath.MEMBER_PICTURE.getValue(), multipartFile);
        memberService.updateMemberPicture(memberMapper.getByMemberId(memberId), uploadRequest);
        return "redirect:/upload";
    }

    @PostMapping("/api/member/auth")
    public ResponseEntity authCheck(@AuthenticationPrincipal Member member) {

        if (member == null) {
            throw new RuntimeException("Not Fount Principal");
        }

        return ResponseEntity.ok(member);
    }
}
