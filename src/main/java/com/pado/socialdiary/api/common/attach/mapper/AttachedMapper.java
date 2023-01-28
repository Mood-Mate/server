package com.pado.socialdiary.api.common.attach.mapper;

import com.pado.socialdiary.api.common.attach.entity.Attached;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachedMapper {

    Attached findMemberPictureByMemberId(Integer memberId);

    void createAttached(Attached attached);
}
