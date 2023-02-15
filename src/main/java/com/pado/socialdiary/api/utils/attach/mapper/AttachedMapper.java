package com.pado.socialdiary.api.utils.attach.mapper;

import java.util.Optional;

import com.pado.socialdiary.api.utils.attach.entity.Attached;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachedMapper {

    Attached findMemberPictureByMemberId(Integer memberId);
    Optional<Integer> findDiaryPictureIdByDiaryId(Integer diaryId);
    void createAttached(Attached attached);
    void deleteAttached(Integer attachedId);
}
