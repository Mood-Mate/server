package com.pado.socialdiary.api.diary;

import com.pado.socialdiary.api.diary.dto.DiaryCreateRequest;
import com.pado.socialdiary.api.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.diary.entity.Diary;
import com.pado.socialdiary.api.diary.service.DiaryService;
import com.pado.socialdiary.api.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryRestController {

    private final DiaryService diaryService;

    @PostMapping("")
    public ResponseEntity createDiary(@AuthenticationPrincipal Member member,
                                    @RequestBody DiaryCreateRequest diaryCreateRequest){

        diaryService.createDiary(member, diaryCreateRequest);

        return ResponseEntity.ok()
            .build();
    }

    @GetMapping("/someone")
    public ResponseEntity<List<Diary>> searchSomeoneDiary(@RequestParam("memberId") Integer memberId,
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
                                    @RequestBody DiaryUpdateRequest diaryUpdateRequest){

        diaryService.updateDiary(member, diaryUpdateRequest);

        return ResponseEntity.ok()
            .build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteDiary(@RequestBody Map<String, Integer> param){

        diaryService.deleteDiary(param.get("diaryId"));

        return ResponseEntity.ok()
            .build();
    }

    @PostMapping("/reply/{diaryId}")
    public ResponseEntity createDiaryComment(@PathVariable("diaryId") Integer diaryId,
                                             @AuthenticationPrincipal Member member,
                                             @RequestBody String comment) {
        diaryService.createDiaryComment(diaryId, member, comment);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/reply/{diaryCommentId}")
    public ResponseEntity deleteDiaryComment(@PathVariable("diaryCommentId") Integer diaryCommentId,
                                             @AuthenticationPrincipal Member member) {
        diaryService.deleteDiaryComment(diaryCommentId, member);

        return ResponseEntity.ok()
                .build();
    }
}
