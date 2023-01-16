package com.pado.socialdiary.api.diary.mapper;

import com.pado.socialdiary.api.diary.entity.Diary;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiaryMapper {

  void insert(Diary diary);

  void update(Diary diary);

  List<Diary> select(Integer memberId);
  List<Diary> select(Integer memberId, String regDt);
}
