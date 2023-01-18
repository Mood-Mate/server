package com.pado.socialdiary.api.member.mapper;

import com.pado.socialdiary.api.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberMapperTest {

    @Autowired
    MemberMapper memberMapper;

    @Test
    @Transactional
    void returnKeyTest() {
    }
}
