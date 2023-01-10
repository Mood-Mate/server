package com.pado.socialdiary.api.diary.mapper;

import com.pado.socialdiary.api.diary.entity.Diary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiaryMapper {

  void create(Diary diary);
}
