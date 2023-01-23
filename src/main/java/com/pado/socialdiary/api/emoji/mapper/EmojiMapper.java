package com.pado.socialdiary.api.emoji.mapper;

import com.pado.socialdiary.api.emoji.dto.SympathyRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface EmojiMapper {

    Optional<Integer> existSympathy(Integer memberId, Integer diaryId);

    void createSympathy(SympathyRequest request);

    void deleteSympathy(SympathyRequest request);
}
