package com.pado.socialdiary.api.moduel.guestbook.mapper;

import java.util.List;

import com.pado.socialdiary.api.moduel.guestbook.dto.GuestBookResponse;
import com.pado.socialdiary.api.moduel.guestbook.entity.GuestBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GuestBookMapper {

    void saveGuestBook(GuestBook guestBook);
    List<GuestBookResponse> findGuestBook(@Param("guestBookId") Integer guestBookId, @Param("memberId") Integer memberId);
    void deleteGuestBook(Integer guestBookId);
}
