package com.pado.socialdiary.api.diary.service;

import com.pado.socialdiary.api.diary.dto.DiaryCreateRequest;
import com.pado.socialdiary.api.diary.dto.DiarySearchRequest;
import com.pado.socialdiary.api.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.diary.entity.Diary;
import com.pado.socialdiary.api.diary.mapper.DiaryMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;

    @Transactional
    public void create(DiaryCreateRequest diaryCreateRequest){
        Diary builtDiary = Diary.builder()
            .memberId(diaryCreateRequest.getMemberId())
            .title(diaryCreateRequest.getTitle())
            .contents(diaryCreateRequest.getContents())
            .build();

        diaryMapper.insert(builtDiary);
    }

    public List<Diary> findOneByDiaryId(Integer diaryId){
        List<Diary> select = diaryMapper.select(diaryId);
        System.out.println("select = " + select);
        return select;
    }


    @Transactional
    public void editDiary(DiaryUpdateRequest diaryUpdateRequest){
        Diary newDiary = Diary.builder()
            .diaryId(diaryUpdateRequest.getDiaryId())
            .memberId(diaryUpdateRequest.getMemberId())
            .title(diaryUpdateRequest.getTitle())
            .contents(diaryUpdateRequest.getContents())
            .build();

        Diary oldDiary = findOneByDiaryId(diaryUpdateRequest.getDiaryId()).get(0);

        Integer regId = oldDiary.getRegId();
        LocalDateTime regDt = oldDiary.getRegDt();

        newDiary.setRegId(regId);
        newDiary.setRegDt(regDt);

        diaryMapper.update(newDiary);
    }

    @Transactional
    public void deleteDiary(){

    }

    public List<Diary> search(DiarySearchRequest diarySearchRequest){
        List<Diary> diaryList = null;

        Integer memberId = diarySearchRequest.getMemberId();
        String regDt = diarySearchRequest.getRegDt();

        if(memberId==null || regDt==null){
            System.out.println("null 예외 처리");
        }

        diaryList = diaryMapper.select(memberId, regDt);

        return diaryList;
    }
}
