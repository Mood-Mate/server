package com.pado.socialdiary.api.guest_book.service;

import com.pado.socialdiary.api.guest_book.entity.GuestBook;
import com.pado.socialdiary.api.guest_book.mapper.GuestBookMapper;
import com.pado.socialdiary.api.member.entity.Member;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuestBookService {
    private final GuestBookMapper guestBookMapper;

    @Transactional
    public void createGuestBook(Integer hostMemberId, Member member, String contents) {

        GuestBook builtGuestBook = GuestBook.builder()
                .hostMemberId(hostMemberId)
                .guestMemberId(member.getMemberId())
                .contents(contents)
                .build();

        guestBookMapper.saveGuestBook(builtGuestBook);
    }

    public List<GuestBook> findGuestBook(Integer memberId) {

        return guestBookMapper.findGuestBook(memberId);
    }

    @Transactional
    public void deleteGuestBook(Integer guestBookId) {

        guestBookMapper.deleteGuestBook(guestBookId);
    }
}
