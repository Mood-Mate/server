package com.pado.socialdiary.api.moduel.guestbook;

import com.pado.socialdiary.api.moduel.guestbook.entity.GuestBook;
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
    public ResponseEntity createGuestBook(@PathVariable("hostMemberId") Integer hostMemberId,
                                          @AuthenticationPrincipal Member member,
                                          @RequestBody String contents) {

        guestBookService.createGuestBook(hostMemberId, member, contents);

        return ResponseEntity.ok()
            .build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<GuestBook>> findGuestBook(@PathVariable("memberId") Integer memberId) {

        return ResponseEntity.ok(guestBookService.findGuestBook(memberId));
    }

    @DeleteMapping("/{guestBookId}")
    public ResponseEntity deleteGuestBook(@PathVariable("guestBookId") Integer guestBookId) {

        guestBookService.deleteGuestBook(guestBookId);

        return ResponseEntity.ok()
            .build();
    }
}
