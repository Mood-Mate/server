package com.pado.socialdiary.api.moduel.emoji.mapper;

import com.pado.socialdiary.api.moduel.emoji.dto.SympathyRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface EmojiMapper {

    Optional<Integer> existSympathy(@Param("memberId") Integer memberId, @Param("diaryId") Integer diaryId);

    void createSympathy(SympathyRequest request);

    void deleteSympathy(SympathyRequest request);
}
