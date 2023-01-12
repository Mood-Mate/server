package com.pado.socialdiary.api.guest_book;

import com.pado.socialdiary.api.guest_book.dto.GuestBookRequest;
import com.pado.socialdiary.api.guest_book.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guestBook/")
public class GuestBookRestController {
    private final GuestBookService guestBookService;

    @PostMapping("")
    public ResponseEntity add(@RequestBody GuestBookRequest guestBookRequest) {

        guestBookService.add(guestBookRequest);

        return ResponseEntity.ok()
                .build();
    }

}
