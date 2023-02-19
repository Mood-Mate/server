package com.pado.socialdiary.api.moduel.guestbook.mapper;

import java.util.List;

import com.pado.socialdiary.api.moduel.guestbook.entity.GuestBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GuestBookMapper {

    void saveGuestBook(GuestBook guestBook);
    List<GuestBook> findGuestBook(Integer memberId);
    void deleteGuestBook(Integer guestBookId);
}