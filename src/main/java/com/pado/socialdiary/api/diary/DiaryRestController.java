package com.pado.socialdiary.api.diary;

import com.pado.socialdiary.api.diary.dto.DiaryCommentResponse;
import com.pado.socialdiary.api.diary.dto.DiaryCreateRequest;
import com.pado.socialdiary.api.diary.dto.DiaryResponse;
import com.pado.socialdiary.api.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.diary.entity.Diary;
import com.pado.socialdiary.api.diary.service.DiaryService;
import com.pado.socialdiary.api.member.entity.Member;
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
    public ResponseEntity<List<DiaryResponse>> searchSomeoneDiary(@RequestParam("memberId") Integer memberId,
                                                                  @RequestParam("regDt") String regDt){

        return ResponseEntity.ok(diaryService.findSomeoneDiary(memberId, regDt));
    }

    @GetMapping("/someone/date")
    public ResponseEntity<List<String>> searchSomeoneDate(@RequestParam("memberId") Integer memberId,
                                                          @RequestParam("regDt") String regDt){

        return ResponseEntity.ok(diaryService.findDateOfMonth(memberId, regDt));
    }

    @GetMapping("/followee")
    public ResponseEntity<List<Diary>> searchFolloweeDiary(@AuthenticationPrincipal Member member){

        return ResponseEntity.ok(diaryService.findFolloweeDiary(member));
    }

    @PutMapping("/edit")
    public ResponseEntity editDiary(@AuthenticationPrincipal Member member,
                                    @RequestPart DiaryUpdateRequest diaryUpdateRequest,
                                    @RequestPart(value = "picture", required = false) MultipartFile multipartFile) throws IOException {

        diaryService.updateDiary(member, diaryUpdateRequest, multipartFile);

        return ResponseEntity.ok()
            .build();
    }

    @DeleteMapping("/delete/{diaryPictureId}")
    public ResponseEntity deleteDiaryPicture(@PathVariable("diaryPictureId") Integer diaryPictureId){

        diaryService.deleteDiaryPicture(diaryPictureId);

        return ResponseEntity.ok()
            .build();
    }

    @DeleteMapping("/delete/{diaryId}")
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
