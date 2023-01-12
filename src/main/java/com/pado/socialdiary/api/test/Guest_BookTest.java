package com.pado.socialdiary.api.test;

import com.pado.socialdiary.api.guest_book.dto.GuestBookRequest;
import com.pado.socialdiary.api.guest_book.mapper.GuestBookMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class Guest_BookTest {

    @Autowired
    GuestBookMapper guestBookMapper;
}
