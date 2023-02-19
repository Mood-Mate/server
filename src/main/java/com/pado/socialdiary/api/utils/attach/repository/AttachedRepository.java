package com.pado.socialdiary.api.utils.attach.repository;

import com.pado.socialdiary.api.utils.attach.entity.Attached;

import java.util.Optional;

public interface AttachedRepository {

    Attached findMemberPictureByMemberId(Integer memberId);
    Optional<Integer> findDiaryPictureIdByDiaryId(Integer diaryId);
    void createAttached(Attached attached);
    void deleteAttached(Integer attachedId);
}
