package com.pado.socialdiary.api.diary.service;

import com.pado.socialdiary.api.diary.dto.DiaryCreateRequest;
import com.pado.socialdiary.api.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.diary.entity.Diary;
import com.pado.socialdiary.api.diary.entity.DiaryComment;
import com.pado.socialdiary.api.diary.entity.DiaryHistory;
import com.pado.socialdiary.api.diary.mapper.DiaryMapper;
import com.pado.socialdiary.api.follow.mapper.FollowMapper;
import com.pado.socialdiary.api.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;
    private final FollowMapper followMapper;

    @Transactional
    public void createDiary(Member member, DiaryCreateRequest diaryCreateRequest) {

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
    public void updateDiary(Member member, DiaryUpdateRequest diaryUpdateRequest) {

        diaryUpdateRequest.setMemberId(member.getMemberId());
        diaryMapper.update(diaryUpdateRequest);

        Diary getDiary = diaryMapper.getByDiaryId(diaryUpdateRequest.getDiaryId());
        diaryMapper.saveHistory(new DiaryHistory(getDiary));
    }

    @Transactional
    public void deleteDiary(Integer diaryId) {

        diaryMapper.deleteHistory(diaryId);
        diaryMapper.delete(diaryId);
    }

    public List<Diary> findSomeoneDiary(Integer memberId, String regDt) {

        return diaryMapper.select(memberId, regDt);
    }

    public List<String> findDateOfMonth(Integer memberId, String regDt) {

        return diaryMapper.selectDate(memberId, regDt);
    }

    public List<Diary> findFolloweeDiary(Member member) {

        List<Integer> followeeList = followMapper.findFollowee(member.getMemberId());
        followeeList.add(member.getMemberId());
        return diaryMapper.selectAll(followeeList);
    }

    @Transactional
    public void createDiaryComment(Integer diaryId, Member member, String comment) {
        diaryMapper.saveDiaryComment(
                new DiaryComment(diaryId, member.getMemberId(), comment)
        );
    }

    @Transactional
    public void deleteDiaryComment(Integer diaryCommentId, Member member) {
        DiaryComment findDiaryComment = diaryMapper.findDiaryCommentById(diaryCommentId)
                .orElseThrow(() -> new RuntimeException("NOT FOUND ENTITY"));

        if (!findDiaryComment.verifyCreator(member.getMemberId())) {
            throw new RuntimeException("댓글을 작성한 회원이 일치하지 않습니다.");
        }

        findDiaryComment.delete();
        findDiaryComment.updateBy(member.getMemberId());
        diaryMapper.deleteDiaryComment(findDiaryComment);
    }
}
