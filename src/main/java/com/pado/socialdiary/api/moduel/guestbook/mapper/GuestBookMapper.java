package com.pado.socialdiary.api.moduel.guestbook.mapper;

import java.util.List;

import com.pado.socialdiary.api.moduel.guestbook.dto.GuestBookResponse;
import com.pado.socialdiary.api.moduel.guestbook.entity.GuestBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GuestBookMapper {

    void saveGuestBook(GuestBook guestBook);
    List<GuestBookResponse> findGuestBook(Integer guestBookId, Integer memberId);
    void deleteGuestBook(Integer guestBookId);
}
