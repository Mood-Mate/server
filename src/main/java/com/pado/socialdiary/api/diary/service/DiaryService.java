package com.pado.socialdiary.api.diary.service;

import com.pado.socialdiary.api.diary.dto.DiaryCommentResponse;
import com.pado.socialdiary.api.diary.dto.DiaryCreateRequest;
import com.pado.socialdiary.api.diary.dto.DiaryResponse;
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
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * 회원 프로필 다이어리 리스트
     * @param memberId
     * @param regDt
     * @return
     */
    public List<DiaryResponse> findSomeoneDiary(Integer memberId, String regDt) {

        List<DiaryResponse> findDiary = diaryMapper.select(memberId, regDt);

        Map<Integer, List<DiaryCommentResponse>> findDiaryCommentMap = diaryMapper.findDiaryCommentsByDiaryIds(
                        findDiary.stream()
                                .map(diary -> diary.getDiaryId())
                                .toList()
                ).stream()
                .collect(Collectors.groupingBy(diaryComment -> diaryComment.getDiaryId()));

        findDiary.forEach(diary -> {
            diary.setComments(findDiaryCommentMap.get(diary.getDiaryId()));
        });

        return findDiary;
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
    public List<DiaryCommentResponse> createDiaryComment(Integer diaryId, Member member, String comment) {
        diaryMapper.saveDiaryComment(
                new DiaryComment(diaryId, member.getMemberId(), comment)
        );

        return diaryMapper.findDiaryCommentsByDiaryId(diaryId);
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
