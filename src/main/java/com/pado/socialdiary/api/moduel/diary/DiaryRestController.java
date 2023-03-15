package com.pado.socialdiary.api.moduel.diary;

import com.pado.socialdiary.api.utils.pageable.entity.CursorPageable;
import com.pado.socialdiary.api.utils.pageable.dto.CursorPageResponse;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryCommentResponse;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryCreateRequest;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryResponse;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.moduel.diary.service.DiaryService;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryRestController {

    private final DiaryService diaryService;

    @PostMapping("")
    public ResponseEntity createDiary(@AuthenticationPrincipal Member member,
                                      @RequestPart DiaryCreateRequest diaryCreateRequest,
                                      @RequestPart(value = "picture", required = false) MultipartFile multipartFile) throws IOException {

        diaryService.createDiary(member, diaryCreateRequest, multipartFile);

        return ResponseEntity.ok()
            .build();
    }

    @GetMapping("/someone")
    public ResponseEntity<List<DiaryResponse>> searchSomeoneDiary(@AuthenticationPrincipal Member member,
                                                                  @RequestParam("someone") Integer someoneId,
                                                                  @RequestParam("regDt") String regDt){

        return ResponseEntity.ok(diaryService.findSomeoneDiary(member, someoneId, regDt));
    }

    @GetMapping("/someone/date")
    public ResponseEntity<List<String>> searchSomeoneDate(@AuthenticationPrincipal Member member,
                                                          @RequestParam("someone") Integer someoneId,
                                                          @RequestParam("regDt") String regDt){

        return ResponseEntity.ok(diaryService.findDateOfMonth(member, someoneId, regDt));
    }

    @GetMapping("/following")
    public ResponseEntity<CursorPageResponse<DiaryResponse>> searchFollowingDiary(@AuthenticationPrincipal Member member,
                                                                                  @RequestParam(required = false, value = "next") Integer next) {

        CursorPageable cursorPageable = new CursorPageable();
        cursorPageable.setCursor(next);
        return ResponseEntity.ok(diaryService.findFollowingDiary(member, cursorPageable));
    }

    @PatchMapping("/edit")
    public ResponseEntity editDiary(@AuthenticationPrincipal Member member,
                                    @RequestPart DiaryUpdateRequest diaryUpdateRequest,
                                    @RequestPart(value = "picture", required = false) MultipartFile multipartFile) throws IOException {

        diaryService.updateDiary(member, diaryUpdateRequest, multipartFile);

        return ResponseEntity.ok()
            .build();
    }

    @DeleteMapping("/picture/{diaryId}")
    public ResponseEntity deleteDiaryPicture(@PathVariable("diaryId") Integer diaryId){

        diaryService.deleteDiaryPicture(diaryId);

        return ResponseEntity.ok()
            .build();
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity deleteDiary(@PathVariable("diaryId") Integer diaryId){

        diaryService.deleteDiary(diaryId);

        return ResponseEntity.ok()
            .build();
    }

    @PostMapping("/reply/{diaryId}")
    public ResponseEntity createDiaryComment(@PathVariable("diaryId") Integer diaryId,
                                             @AuthenticationPrincipal Member member,
                                             @RequestBody String comment) {
        List<DiaryCommentResponse> result = diaryService.createDiaryComment(diaryId, member, comment);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/reply/{diaryCommentId}")
    public ResponseEntity deleteDiaryComment(@PathVariable("diaryCommentId") Integer diaryCommentId,
                                             @AuthenticationPrincipal Member member) {
        diaryService.deleteDiaryComment(diaryCommentId, member);

        return ResponseEntity.ok()
                .build();
    }
}
