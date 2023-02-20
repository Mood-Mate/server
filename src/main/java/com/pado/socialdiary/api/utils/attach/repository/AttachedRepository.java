package com.pado.socialdiary.api.utils.attach.repository;

import com.pado.socialdiary.api.utils.attach.entity.Attached;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface AttachedRepository {

    Attached findMemberPictureByMemberId(Integer memberId);
    Optional<Integer> findDiaryPictureIdByDiaryId(Integer diaryId);
    void createAttached(Attached attached);
    void deleteAttached(Integer attachedId);
    void deleteMemberPicture(Integer memberId, String refTable, String attachedPath);
}
