package com.pado.socialdiary.api.moduel.diary.mapper;

import com.pado.socialdiary.api.moduel.diary.dto.DiaryCommentResponse;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryResponse;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.moduel.diary.entity.Diary;
import com.pado.socialdiary.api.moduel.diary.entity.DiaryComment;
import com.pado.socialdiary.api.moduel.diary.entity.DiaryHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
  List<DiaryResponse> select(@Param("memberId") Integer memberId, @Param("regDt") String regDt);
  List<String> selectDate(@Param("memberId") Integer memberId, @Param("regDt") String regDt);
  List<DiaryResponse> selectAll(Map<String, Object> map);

  List<DiaryCommentResponse> findDiaryCommentsByDiaryIds(List<Integer> diaryIds);
  List<DiaryCommentResponse> findDiaryCommentsByDiaryId(Integer diaryId);
  Optional<DiaryComment> findDiaryCommentById(Integer diaryCommentId);
  void saveDiaryComment(DiaryComment diaryComment);
  void deleteDiaryComment(DiaryComment diaryComment);
}
