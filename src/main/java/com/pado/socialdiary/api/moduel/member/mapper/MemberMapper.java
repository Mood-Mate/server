package com.pado.socialdiary.api.moduel.member.mapper;

import com.pado.socialdiary.api.moduel.follow.entity.Follow;
import com.pado.socialdiary.api.moduel.member.dto.MemberSearchResponse;
import com.pado.socialdiary.api.moduel.member.dto.MemberUpdateRequest;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.entity.MemberHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
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

    void updateIntroduce(Integer memberId, String introduce);

    List<MemberSearchResponse> findMemberByKeyword(String keyword);

    List<Follow> findFollowMember(Integer mmemberId);
}
