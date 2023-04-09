package com.pado.socialdiary.api.moduel.emoji.mapper;

import com.pado.socialdiary.api.moduel.emoji.dto.SympathyRequest;
import com.pado.socialdiary.api.moduel.emoji.dto.SympathyResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmojiMapper {

    Optional<Integer> existSympathy(@Param("memberId") Integer memberId, @Param("diaryId") Integer diaryId);

    void createSympathy(SympathyRequest request);

    void deleteSympathy(SympathyRequest request);

    List<SympathyResponse> findSympathyByMemberIdAndDiaryIds(@Param("memberId") Integer memberId,
                                                             @Param("diaryIds") List<Integer> diaryIds);

    SympathyResponse findSympathy(@Param("memberId") Integer memberId,
                                  @Param("diaryId") Integer diaryId);
}
