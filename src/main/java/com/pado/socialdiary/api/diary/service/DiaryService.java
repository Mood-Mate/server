package com.pado.socialdiary.api.diary.service;

import com.pado.socialdiary.api.common.attach.AttachUtil;
import com.pado.socialdiary.api.common.attach.dto.AttachDto;
import com.pado.socialdiary.api.common.attach.entity.Attached;
import com.pado.socialdiary.api.common.attach.mapper.AttachedMapper;
import com.pado.socialdiary.api.common.constants.AttachPath;
import com.pado.socialdiary.api.common.constants.RefTable;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;
    private final FollowMapper followMapper;
    private final AttachUtil attachUtil;
    private final AttachedMapper attachedMapper;

    @Transactional
    public void createDiary(Member member, DiaryCreateRequest diaryCreateRequest, MultipartFile multipartFile)
            throws IOException {

        Diary builtDiary = Diary.builder()
                .memberId(member.getMemberId())
                .title(diaryCreateRequest.getTitle())
                .contents(diaryCreateRequest.getContents())
                .build();

        diaryMapper.insert(builtDiary);

        if (multipartFile != null) {
            AttachDto.UploadRequest uploadRequest = attachUtil.attachedFile(
                    AttachPath.DIARY_PICTURE.getValue(), multipartFile);

            Attached builtDiaryPicture = Attached.builder()
                    .refTable(RefTable.TB_DIARY.getValue())
                    .refId(builtDiary.getDiaryId())
                    .originalFilename(uploadRequest.getOriginalFileName())
                    .attachedFilename(uploadRequest.getAttachedFileName())
                    .attachedPath(AttachPath.DIARY_PICTURE.getValue())
                    .fileSize(uploadRequest.getFileSize())
                    .regId(member.getMemberId())
                    .regDt(LocalDateTime.now())
                    .updId(member.getMemberId())
                    .updDt(LocalDateTime.now())
                    .build();

            attachedMapper.createAttached(builtDiaryPicture);
        }

        Diary getDiary = diaryMapper.getByDiaryId(builtDiary.getDiaryId());
        diaryMapper.saveHistory(new DiaryHistory(getDiary));
    }

    @Transactional
    public void updateDiary(Member member, DiaryUpdateRequest diaryUpdateRequest, MultipartFile multipartFile)
            throws IOException {

        if (multipartFile != null) {
            Optional<Integer> oldPictureId = attachedMapper.findDiaryPictureIdByDiaryId(diaryUpdateRequest.getDiaryId());
            if (oldPictureId.isPresent()) {
                attachedMapper.deleteAttached(oldPictureId.get());
            }

            AttachDto.UploadRequest uploadRequest = attachUtil.attachedFile(
                    AttachPath.DIARY_PICTURE.getValue(), multipartFile);

            Attached builtDiaryPicture = Attached.builder()
                    .refTable(RefTable.TB_DIARY.getValue())
                    .refId(diaryUpdateRequest.getDiaryId())
                    .originalFilename(uploadRequest.getOriginalFileName())
                    .attachedFilename(uploadRequest.getAttachedFileName())
                    .attachedPath(AttachPath.DIARY_PICTURE.getValue())
                    .fileSize(uploadRequest.getFileSize())
                    .regId(member.getMemberId())
                    .regDt(LocalDateTime.now())
                    .updId(member.getMemberId())
                    .updDt(LocalDateTime.now())
                    .build();

            attachedMapper.createAttached(builtDiaryPicture);
        }

        diaryUpdateRequest.setMemberId(member.getMemberId());
        diaryMapper.update(diaryUpdateRequest);

        Diary getDiary = diaryMapper.getByDiaryId(diaryUpdateRequest.getDiaryId());
        diaryMapper.saveHistory(new DiaryHistory(getDiary));
    }

    @Transactional
    public void deleteDiaryPicture(Integer diaryPictureId) {

        attachedMapper.deleteAttached(diaryPictureId);
    }

    @Transactional
    public void deleteDiary(Integer diaryId) {

        Optional<Integer> diaryPictureId = attachedMapper.findDiaryPictureIdByDiaryId(diaryId);
        if (diaryPictureId.isPresent()) {
            attachedMapper.deleteAttached(diaryPictureId.get());
        }

        diaryMapper.deleteHistory(diaryId);
        diaryMapper.delete(diaryId);
    }

    /**
     * 회원 프로필 다이어리 리스트
     *
     * @param memberId
     * @param regDt
     * @return
     */
    public List<DiaryResponse> findSomeoneDiary(Integer memberId, String regDt) {

        List<DiaryResponse> findDiary = diaryMapper.select(memberId, regDt);


        if (findDiary.size() != 0) {
            Map<Integer, List<DiaryCommentResponse>> findDiaryCommentMap = diaryMapper.findDiaryCommentsByDiaryIds(
                            findDiary.stream()
                                    .map(diary -> diary.getDiaryId())
                                    .toList()
                    ).stream()
                    .collect(Collectors.groupingBy(diaryComment -> diaryComment.getDiaryId()));

            findDiary.forEach(diary -> {
                diary.setComments(findDiaryCommentMap.get(diary.getDiaryId()));
            });
        }

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
