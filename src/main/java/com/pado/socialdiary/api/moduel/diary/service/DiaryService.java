package com.pado.socialdiary.api.moduel.diary.service;

import com.pado.socialdiary.api.utils.attach.AttachUtil;
import com.pado.socialdiary.api.utils.attach.dto.AttachDto;
import com.pado.socialdiary.api.utils.attach.entity.Attached;
import com.pado.socialdiary.api.utils.attach.repository.AttachedMapper;
import com.pado.socialdiary.api.constants.AttachPath;
import com.pado.socialdiary.api.constants.RefTable;
import com.pado.socialdiary.api.utils.pageable.entity.CursorPageable;
import com.pado.socialdiary.api.utils.pageable.dto.CursorPageResponse;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryCommentResponse;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryCreateRequest;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryResponse;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.moduel.diary.entity.Diary;
import com.pado.socialdiary.api.moduel.diary.entity.DiaryComment;
import com.pado.socialdiary.api.moduel.diary.entity.DiaryHistory;
import com.pado.socialdiary.api.moduel.diary.mapper.DiaryMapper;
import com.pado.socialdiary.api.moduel.follow.mapper.FollowMapper;
import com.pado.socialdiary.api.moduel.member.entity.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    public CursorPageResponse<DiaryResponse> findFolloweeDiary(Member member, CursorPageable cursorPageable) {

        List<Integer> followeeList = followMapper.findFolloweeId(member.getMemberId());

        Map<String, Object> map = new HashMap<>();
        map.put("followeeList", followeeList);
        map.put("cursorPageable", cursorPageable);

        List<DiaryResponse> diaryList = diaryMapper.selectAll(map);
        final Integer pageSize = cursorPageable.getPageSize();
        final Integer next = diaryList.size() >= pageSize ? diaryList.get(pageSize - 1).getDiaryId() : null;

        return CursorPageResponse.<DiaryResponse>builder()
            .data(diaryList)
            .next(next)
            .build();
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
