package com.pado.socialdiary.api.diary.mapper;

import com.pado.socialdiary.api.diary.dto.DiarySearchRequest;
import com.pado.socialdiary.api.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.diary.entity.Diary;
import com.pado.socialdiary.api.diary.entity.DiaryHistory;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiaryMapper {

  void insert(Diary diary);
  void update(DiaryUpdateRequest diaryUpdateRequest);
  void delete(Integer diaryId);
  Diary getByDiaryId(Integer diaryId);
  void saveHistory(DiaryHistory diaryHistory);
  void deleteHistory(Integer diaryId);
  List<Diary> select(DiarySearchRequest diarySearchRequest);
  List<Diary> selectAll(List<Integer> followeeList);

}
