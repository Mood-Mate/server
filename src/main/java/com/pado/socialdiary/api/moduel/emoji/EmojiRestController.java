package com.pado.socialdiary.api.moduel.emoji;

import com.pado.socialdiary.api.moduel.emoji.dto.SympathyRequest;
import com.pado.socialdiary.api.moduel.emoji.service.EmojiService;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emoji")
public class EmojiRestController {

    private final EmojiService emojiService;

    @PatchMapping("/sympathy")
    public ResponseEntity sympathy(@AuthenticationPrincipal Member member,
                                   @RequestBody SympathyRequest request) {

        emojiService.createOrDeleteSympathy(member, request);

        return ResponseEntity.ok()
                .build();
    }
}
