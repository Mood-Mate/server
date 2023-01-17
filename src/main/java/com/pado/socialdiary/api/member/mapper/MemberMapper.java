package com.pado.socialdiary.api.member.mapper;

import com.pado.socialdiary.api.member.dto.MemberUpdateRequest;
import com.pado.socialdiary.api.member.entity.Member;
import com.pado.socialdiary.api.member.entity.MemberHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    Integer findDuplicateEmailCount(String email);

    void save(Member member);

    void saveHistory(MemberHistory memberHistory);

    Optional<Member> findByEmail(String email);

    Member getByMemberId(Integer memberId);

    void updateRegOrUpdColumn(Integer memberId);

    void update(MemberUpdateRequest memberUpdateRequest);
}
