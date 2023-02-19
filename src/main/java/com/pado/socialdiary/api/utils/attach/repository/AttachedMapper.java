package com.pado.socialdiary.api.utils.attach.repository;

import com.pado.socialdiary.api.utils.attach.entity.Attached;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AttachedMapper {

    Attached findMemberPictureByMemberId(Integer memberId);
    Optional<Integer> findDiaryPictureIdByDiaryId(Integer diaryId);
    void createAttached(Attached attached);
    void deleteAttached(Integer attachedId);
}
