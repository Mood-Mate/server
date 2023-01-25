package com.pado.socialdiary.api.diary.service;

import com.pado.socialdiary.api.diary.dto.DiaryCreateRequest;
import com.pado.socialdiary.api.diary.dto.DiarySearchRequest;
import com.pado.socialdiary.api.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.diary.entity.Diary;
import com.pado.socialdiary.api.diary.entity.DiaryHistory;
import com.pado.socialdiary.api.diary.mapper.DiaryMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;

    @Transactional
    public void createDiary(DiaryCreateRequest diaryCreateRequest){
        Diary builtDiary = Diary.builder()
            .memberId(diaryCreateRequest.getMemberId())
            .title(diaryCreateRequest.getTitle())
            .contents(diaryCreateRequest.getContents())
            .build();

        diaryMapper.insert(builtDiary);

        Diary getDiary = diaryMapper.getByDiaryId(builtDiary.getDiaryId());
        diaryMapper.saveHistory(new DiaryHistory(getDiary));
    }

    @Transactional
    public void updateDiary(DiaryUpdateRequest diaryUpdateRequest){
        Diary builtDiary = Diary.builder()
            .diaryId(diaryUpdateRequest.getDiaryId())
            .memberId(diaryUpdateRequest.getMemberId())
            .title(diaryUpdateRequest.getTitle())
            .contents(diaryUpdateRequest.getContents())
            .build();

        diaryMapper.update(builtDiary);

        Diary getDiary = diaryMapper.getByDiaryId(builtDiary.getDiaryId());
        diaryMapper.saveHistory(new DiaryHistory(getDiary));
    }

    @Transactional
    public void deleteDiary(Integer diaryId){

        diaryMapper.deleteHistory(diaryId);
        diaryMapper.delete(diaryId);
    }

    public List<Diary> findDiary(DiarySearchRequest diarySearchRequest){
        Optional<List<Diary>> diaryList = Optional.ofNullable(diaryMapper.select(diarySearchRequest));

        if(diaryList.isEmpty()){
        }

        return diaryList.get();
    }
}
