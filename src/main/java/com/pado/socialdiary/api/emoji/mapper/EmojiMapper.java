package com.pado.socialdiary.api.emoji.mapper;

import com.pado.socialdiary.api.emoji.dto.EmojiSympathyRequest;
import com.pado.socialdiary.api.emoji.entity.EmojiType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmojiMapper {

    void createSympathy(EmojiSympathyRequest request);
}
