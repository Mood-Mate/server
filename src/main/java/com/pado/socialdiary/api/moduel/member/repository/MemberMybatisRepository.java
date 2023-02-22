package com.pado.socialdiary.api.moduel.member.repository;

import com.pado.socialdiary.api.moduel.follow.entity.Follow;
import com.pado.socialdiary.api.moduel.member.dto.MemberProfileResponse;
import com.pado.socialdiary.api.moduel.member.dto.MemberSearchResponse;
import com.pado.socialdiary.api.moduel.member.dto.MemberUpdateRequest;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.entity.MemberHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberMybatisRepository implements MemberRepository {

    private final MemberMapper memberMapper;

    @Override
    public Integer findDuplicateEmailCount(String email) {
        return memberMapper.findDuplicateEmailCount(email);
    }

    @Override
    public void save(Member member) {
        memberMapper.save(member);
    }

    @Override
    public void saveHistory(MemberHistory memberHistory) {
        memberMapper.saveHistory(memberHistory);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberMapper.findByEmail(email);
    }

    @Override
    public Member getByMemberId(Integer memberId) {
        return memberMapper.getByMemberId(memberId);
    }

    @Override
    public void updateRegOrUpdColumn(Integer memberId) {
        memberMapper.updateRegOrUpdColumn(memberId);
    }

    @Override
    public void update(MemberUpdateRequest memberUpdateRequest) {
        memberMapper.update(memberUpdateRequest);
    }

    @Override
    public void updateIntroduce(Integer memberId, String introduce) {
        memberMapper.updateIntroduce(memberId, introduce);
    }

    @Override
    public List<MemberSearchResponse> findMemberByKeyword(String keyword) {
        return memberMapper.findMemberByKeyword(keyword);
    }

    @Override
    public MemberProfileResponse findMemberProfile(Integer memberId) {
        return memberMapper.findMemberProfile(memberId);
    }

    @Override
    public List<Follow> findFollowMember(Integer memberId) {
        return memberMapper.findFollowMember(memberId);
    }

    @Override
    public void deleteMember(Integer memberId) {
        memberMapper.deleteMember(memberId);
    }

    @Override
    public void deleteMemberHistory(Integer memberId) {
        memberMapper.deleteMemberHistory(memberId);
    }

    @Override
    public Integer getFollowerCount(Integer memberId) {
        return memberMapper.getFollowerCount(memberId);
    }

    @Override
    public Integer getFolloweeCount(Integer memberId) {
        return memberMapper.getFolloweeCount(memberId);
    }
}
