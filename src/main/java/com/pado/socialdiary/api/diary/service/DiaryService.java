package com.pado.socialdiary.api.diary.service;

import com.pado.socialdiary.api.diary.dto.DiaryRequest;
import com.pado.socialdiary.api.diary.entity.Diary;
import com.pado.socialdiary.api.diary.mapper.DiaryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;

    @Transactional
    public void create(DiaryRequest diaryRequest){
        Diary builtDiary = Diary.builder()
            .memberId(diaryRequest.getMemberId())
            .title(diaryRequest.getTitle())
            .contents(diaryRequest.getContents())
            .build();

        diaryMapper.create(builtDiary);
    }

    @Transactional
    public void editDiary(DiaryRequest diaryRequest){

    }

    @Transactional
    public void deleteDiary(){

    }
}
