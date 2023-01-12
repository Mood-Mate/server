package com.pado.socialdiary.api.guest_book.service;

import com.pado.socialdiary.api.guest_book.dto.GuestBookRequest;
import com.pado.socialdiary.api.guest_book.entity.GuestBook;
import com.pado.socialdiary.api.guest_book.mapper.GuestBookMapper;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestBookService {
    private final GuestBookMapper guestBookMapper;

    @Transactional
    public void add(GuestBookRequest guestBookRequest) {
        GuestBook guestBook = GuestBook.builder()
                .GuestBookId(guestBookRequest.getGuestBookId())
                .Member_id(guestBookRequest.getMember_Id())
                .Cn(guestBookRequest.getCn())
                .build();

        guestBookMapper.add(guestBook);

    }

    @Transactional
    public void del(int GuestBookId) {
    }
}
