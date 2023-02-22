package com.pado.socialdiary.api.moduel.member.repository;

import com.pado.socialdiary.api.moduel.follow.entity.Follow;
import com.pado.socialdiary.api.moduel.member.dto.MemberProfileResponse;
import com.pado.socialdiary.api.moduel.member.dto.MemberSearchResponse;
import com.pado.socialdiary.api.moduel.member.dto.MemberUpdateRequest;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.entity.MemberHistory;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Integer findDuplicateEmailCount(String email);

    void save(Member member);

    void saveHistory(MemberHistory memberHistory);

    Optional<Member> findByEmail(String email);

    Member getByMemberId(Integer memberId);

    void updateRegOrUpdColumn(Integer memberId);

    void update(MemberUpdateRequest memberUpdateRequest);

    void updateIntroduce(Integer memberId, String introduce);

    List<MemberSearchResponse> findMemberByKeyword(String keyword);

    MemberProfileResponse findMemberProfile(Integer memberId);

    List<Follow> findFollowMember(Integer memberId);

    void deleteMember(Integer memberId);

    void deleteMemberHistory(Integer memberId);

    Integer getFollowerCount(Integer memberId);

    Integer getFolloweeCount(Integer memberId);
}
