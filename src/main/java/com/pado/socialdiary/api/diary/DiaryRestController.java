package com.pado.socialdiary.api.diary;

import com.pado.socialdiary.api.diary.dto.DiaryCreateRequest;
import com.pado.socialdiary.api.diary.dto.DiarySearchRequest;
import com.pado.socialdiary.api.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.diary.entity.Diary;
import com.pado.socialdiary.api.diary.service.DiaryService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryRestController {

    private final DiaryService diaryService;

    @PostMapping("")
    public ResponseEntity createDiary(@RequestBody DiaryCreateRequest diaryCreateRequest){

        diaryService.createDiary(diaryCreateRequest);

        return ResponseEntity.ok()
            .build();
    }

    @GetMapping("")
    public ResponseEntity<List<Diary>> searchDiary(@RequestBody DiarySearchRequest diarySearchRequest){

        return ResponseEntity.ok(diaryService.findDiary(diarySearchRequest));
    }

    @PutMapping("/edit")
    public ResponseEntity editDiary(@RequestBody DiaryUpdateRequest diaryUpdateRequest){

        diaryService.updateDiary(diaryUpdateRequest);

        return ResponseEntity.ok()
            .build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteDiary(@RequestBody Map<String, Integer> param){

        diaryService.deleteDiary(param.get("diaryId"));

        return ResponseEntity.ok()
            .build();
    }
}