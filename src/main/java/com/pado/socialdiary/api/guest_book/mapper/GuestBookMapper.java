package com.pado.socialdiary.api.guest_book.mapper;

import com.pado.socialdiary.api.guest_book.dto.GuestBookRequest;
import com.pado.socialdiary.api.guest_book.entity.GuestBook;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface GuestBookMapper {
    void add(GuestBook guestBook); //글쓰기
    void del(int GuestBookId); //삭제
    
    //읽기 목록 구현

}
