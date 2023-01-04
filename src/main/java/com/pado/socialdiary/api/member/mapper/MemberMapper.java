package com.pado.socialdiary.api.member.mapper;

import com.pado.socialdiary.api.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    void join(Member member);

    Optional<Member> findByEmail(String email);
}
