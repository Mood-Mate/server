package com.pado.socialdiary.api;

import com.pado.socialdiary.api.member.mapper.MemberMapper;
import com.pado.socialdiary.api.member.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ApplicationTests {

	@Autowired
	MemberMapper memberMapper;

	@Autowired
	MemberService memberService;

	@Test
	void contextLoads() {
		Integer resultByMapper = memberMapper.testOne();
		Integer resultByService = memberService.testOne();

		assertEquals(resultByMapper, 1);
		assertEquals(resultByService, 1);
	}
}
