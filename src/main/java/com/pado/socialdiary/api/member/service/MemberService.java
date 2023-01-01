package com.pado.socialdiary.api.member.service;

import com.pado.socialdiary.api.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    @Transactional
    public Integer testOne() {
        return memberMapper.testOne();
    }
}
