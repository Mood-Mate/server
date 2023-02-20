package com.pado.socialdiary.api.utils.attach.repository;

import com.pado.socialdiary.api.utils.attach.entity.Attached;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AttachedMybatisRepository implements AttachedRepository{

    private final AttachedMapper attachedMapper;

    @Override
    public Attached findMemberPictureByMemberId(Integer memberId) {
        return attachedMapper.findMemberPictureByMemberId(memberId);
    }

    @Override
    public Optional<Integer> findDiaryPictureIdByDiaryId(Integer diaryId) {
        return attachedMapper.findDiaryPictureIdByDiaryId(diaryId);
    }

    @Override
    public void createAttached(Attached attached) {
        attachedMapper.createAttached(attached);
    }

    @Override
    public void deleteAttached(Integer attachedId) {
        attachedMapper.deleteAttached(attachedId);
    }

    @Override
    public void deleteMemberPicture(Integer memberId, String refTable, String attachedPath) {
        attachedMapper.deleteMemberPicture(memberId, refTable, attachedPath);
    }
}
