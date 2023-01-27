package com.pado.socialdiary.api.diary.service;

import com.pado.socialdiary.api.diary.dto.DiaryCreateRequest;
import com.pado.socialdiary.api.diary.dto.DiarySearchRequest;
import com.pado.socialdiary.api.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.diary.entity.Diary;
import com.pado.socialdiary.api.diary.entity.DiaryHistory;
import com.pado.socialdiary.api.diary.mapper.DiaryMapper;
import com.pado.socialdiary.api.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;

    @Transactional
    public void createDiary(Member member, DiaryCreateRequest diaryCreateRequest){

        Diary builtDiary = Diary.builder()
            .memberId(member.getMemberId())
            .title(diaryCreateRequest.getTitle())
            .contents(diaryCreateRequest.getContents())
            .build();

        diaryMapper.insert(builtDiary);

        Diary getDiary = diaryMapper.getByDiaryId(builtDiary.getDiaryId());
        diaryMapper.saveHistory(new DiaryHistory(getDiary));
    }

    @Transactional
    public void updateDiary(Member member, DiaryUpdateRequest diaryUpdateRequest){

        diaryUpdateRequest.setMemberId(member.getMemberId());
        diaryMapper.update(diaryUpdateRequest);

        Diary getDiary = diaryMapper.getByDiaryId(diaryUpdateRequest.getDiaryId());
        diaryMapper.saveHistory(new DiaryHistory(getDiary));
    }

    @Transactional
    public void deleteDiary(Integer diaryId){

        diaryMapper.deleteHistory(diaryId);
        diaryMapper.delete(diaryId);
    }

    public List<Diary> findDiary(DiarySearchRequest diarySearchRequest){

        List<Diary> diaryList = diaryMapper.select(diarySearchRequest);

        return diaryList;
    }
}
