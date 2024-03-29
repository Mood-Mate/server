package com.pado.socialdiary.api.moduel.guestbook;

import com.pado.socialdiary.api.moduel.guestbook.dto.GuestBookResponse;
import com.pado.socialdiary.api.moduel.guestbook.service.GuestBookService;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guestBook")
public class GuestBookRestController {
    private final GuestBookService guestBookService;

    @PostMapping("/{hostMemberId}")
    public ResponseEntity<GuestBookResponse> createGuestBook(@PathVariable("hostMemberId") Integer hostMemberId,
                                          @AuthenticationPrincipal Member member,
                                          @RequestBody String contents) {

        return ResponseEntity.ok(guestBookService.createGuestBook(hostMemberId, member, contents));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<GuestBookResponse>> findGuestBook(@PathVariable("memberId") Integer memberId) {

        return ResponseEntity.ok(guestBookService.findGuestBook(memberId));
    }

    @DeleteMapping("/{guestBookId}")
    public ResponseEntity deleteGuestBook(@PathVariable("guestBookId") Integer guestBookId) {

        guestBookService.deleteGuestBook(guestBookId);

        return ResponseEntity.ok()
            .build();
    }
}
