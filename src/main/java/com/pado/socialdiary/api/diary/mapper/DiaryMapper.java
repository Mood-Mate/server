package com.pado.socialdiary.api.diary.mapper;

import com.pado.socialdiary.api.diary.dto.DiaryCommentResponse;
import com.pado.socialdiary.api.diary.dto.DiaryResponse;
import com.pado.socialdiary.api.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.diary.entity.Diary;
import com.pado.socialdiary.api.diary.entity.DiaryComment;
import com.pado.socialdiary.api.diary.entity.DiaryHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface DiaryMapper {

  void insert(Diary diary);
  void update(DiaryUpdateRequest diaryUpdateRequest);
  void delete(Integer diaryId);
  Diary getByDiaryId(Integer diaryId);
  void saveHistory(DiaryHistory diaryHistory);
  void deleteHistory(Integer diaryId);
  List<DiaryResponse> select(Integer memberId, String regDt);
  List<String> selectDate(Integer memberId, String regDt);
  List<Diary> selectAll(Map<String, Object> map);

  List<DiaryCommentResponse> findDiaryCommentsByDiaryIds(List<Integer> diaryIds);
  List<DiaryCommentResponse> findDiaryCommentsByDiaryId(Integer diaryId);
  Optional<DiaryComment> findDiaryCommentById(Integer diaryCommentId);
  void saveDiaryComment(DiaryComment diaryComment);
  void deleteDiaryComment(DiaryComment diaryComment);
}
