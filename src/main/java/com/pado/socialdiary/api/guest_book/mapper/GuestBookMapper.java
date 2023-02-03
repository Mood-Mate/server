package com.pado.socialdiary.api.guest_book.mapper;

import java.util.List;

import com.pado.socialdiary.api.guest_book.entity.GuestBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GuestBookMapper {

    void saveGuestBook(GuestBook guestBook);
    List<GuestBook> findGuestBook(Integer memberId);
    void deleteGuestBook(Integer guestBookId);
}
